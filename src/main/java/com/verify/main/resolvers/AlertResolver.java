package com.verify.main.resolvers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

import com.verify.main.util.CommonUtils;
import com.verify.main.verifyobjs.Alert;

public class AlertResolver {
    private static final String ALERT_YAML_PATH = "/etc/logstash/alerts.yaml";
    
    public static List<Alert> resolveAlertsFromCsNode(String csip) {
        List<Alert> alerts = new ArrayList<Alert>();
        
        return alerts;
    }
    
    @SuppressWarnings("unchecked")
    private static Alert populateAlert(Map<String, Object> mapAlert) {
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
                String freqCount = CommonUtils.nullSafeToString(mapAlert.get("count"));
                alert.setFreqCount(NumberUtils.toInt(freqCount));
            }
            if (mapFreq.containsKey("time")) {
                String freqTime = CommonUtils.nullSafeToString(mapAlert.get("time"));
                alert.setFreqSecs(convertTimeToSecsNum(freqTime));
            }
        }
        
        // TODO: thresh
        
        return alert;
    }
    
    /**
     * "00:05:00" to "300".
     * "01:00:00" to "3600".
     * "60"       to "60".
     * 
     * @param freqTime
     * @return
     */
    private static int convertTimeToSecsNum(String freqTime) {
        int ret = 0;
        
        return ret;
    }
}
