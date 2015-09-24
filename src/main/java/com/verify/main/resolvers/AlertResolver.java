package com.verify.main.resolvers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.Assert;

import com.verify.main.util.CommonUtils;
import com.verify.main.util.SSHUtil;
import com.verify.main.verifyobjs.Alert;

public class AlertResolver {
    private static final Logger logger = LoggerFactory.getLogger(AlertResolver.class);
    private static final int ERROR_TIME = -1;
    private static final int DEFAULT_TIME = 0;
    
    private static final String ALERT_YAML_PATH = "/etc/logstash/alerts.yaml";
    
    @SuppressWarnings("unchecked")
    public static List<Alert> resolveAlertsFromCsNode(String ip, String user, String passwd) {
        List<Alert> alertsToReturn = new ArrayList<Alert>();
        
        String tempFile = System.getProperty("java.io.tmpdir") + "alerts-yaml-" + System.nanoTime();
        SSHUtil sshClient = new SSHUtil(); 
        sshClient.setHost(ip);
        sshClient.setUser(user);
        sshClient.setPassword(passwd);
        
        try {
            sshClient.getFile(ALERT_YAML_PATH, tempFile);
            
            YamlMapFactoryBean yamlFactory = new YamlMapFactoryBean();
            yamlFactory.setResources(new FileSystemResource(tempFile));
            Map<String, Object> doc = yamlFactory.getObject();
            Object rootElement = doc.get(CommonUtils.ROOT_ELEM_NAME_4YML);
            List<Map<String, Object>> ymlAlerts = (List<Map<String, Object>>) rootElement;
            for (Map<String, Object> mapAlert : ymlAlerts) {
                alertsToReturn.add(populateAlert(mapAlert));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            FileUtils.deleteQuietly(new File(tempFile)); 
        }
        
        return alertsToReturn;
    }
    
    @SuppressWarnings("unchecked")
    public static Alert populateAlert(Map<String, Object> mapAlert) {
        Alert alert = new Alert();
        if (mapAlert.containsKey("name")) {
            alert.setName(CommonUtils.nullSafeToString(mapAlert.get("name")));
        }
        if (mapAlert.containsKey("pattern")) {
            alert.setPattern(CommonUtils.nullSafeToString(mapAlert.get("pattern")));
        }
        if (mapAlert.containsKey("trap")) {
            String trap = CommonUtils.nullSafeToString(mapAlert.get("trap"));
            alert.setTrap(NumberUtils.toInt(trap));
        }
        
        if (mapAlert.containsKey("frequency")) {
            Assert.isInstanceOf(Map.class, mapAlert.get("frequency"));
            Map<String, Object> mapFreq = (Map<String, Object>) mapAlert.get("frequency");
            if (mapFreq.containsKey("count")) {
                String freqCount = CommonUtils.nullSafeToString(mapFreq.get("count"));
                alert.setFreqCount(NumberUtils.toInt(freqCount));
            }
            if (mapFreq.containsKey("time")) {
                String freqTime = CommonUtils.nullSafeToString(mapFreq.get("time"));
                alert.setFreqSecs(convertTimeToSecsNum(freqTime));
            }
        }
        
        if (mapAlert.containsKey("threshold")) {
            Assert.isInstanceOf(Map.class, mapAlert.get("threshold"));
            Map<String, Object> mapThresh = (Map<String, Object>) mapAlert.get("threshold");
            if (mapThresh.containsKey("count")) {
                String threshCount = CommonUtils.nullSafeToString(mapThresh.get("count"));
                alert.setThreshCount(NumberUtils.toInt(threshCount));
            }
            if (mapThresh.containsKey("time")) {
                String threshTime = CommonUtils.nullSafeToString(mapThresh.get("time"));
                alert.setThreshSeconds(convertTimeToSecsNum(threshTime));
            }
        }
        
        return alert;
    }
    
    /**
     * "01:00:00" to "3600".
     * "00:05:00" to "300".
     * "60"       to "60".
     * 
     * @param freqTime
     * @return
     */
    private static int convertTimeToSecsNum(String freqTime) {
        if (freqTime == null || StringUtils.isBlank(freqTime)) {
            return DEFAULT_TIME;
        }
        
        freqTime = freqTime.trim();
        if (!freqTime.contains(":")) {
            return NumberUtils.toInt(freqTime, DEFAULT_TIME);
        }
        
        Matcher m = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})").matcher(freqTime);
        if (!m.find()) {
            logger.error("Invalid format of frequency time: " + freqTime);
            return ERROR_TIME;
        }
        
        int hr2secs = NumberUtils.toInt(m.group(1)) * 3600;
        int min2secs = NumberUtils.toInt(m.group(2)) * 60;
        int secs = NumberUtils.toInt(m.group(3));
        
        return hr2secs + min2secs + secs;
    }
}
