package com.verify.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.verify.main.resolvers.AlertResolver;
import com.verify.main.resolvers.HostResolver;
import com.verify.main.util.CommonUtils;
import com.verify.main.util.ErrorInfoHelper;
import com.verify.main.util.GsonUtil;
import com.verify.main.validators.AlertValidator;
import com.verify.main.validators.HostValidator;
import com.verify.main.verifyobjs.Alert;
import com.verify.main.verifyobjs.Component;
import com.verify.main.verifyobjs.Host;

@Service
public class VerifyService {
    private static final Logger logger = LoggerFactory.getLogger(VerifyService.class);
    
    public static final String KEY_ORACLE = "oracle";
    public static final String KEY_POSTGRES = "postgresql";
    public static final String KEY_CS_NODES = "cluster_service_nodes";
    public static final String KEY_APP_NODES = "cluster_app_nodes";

    public void performVerify() {
        System.out.println("performVerify");
    }

    @SuppressWarnings("unchecked")
    public static void verify(List<Component> expectedComponents, String sysInfoJsonPath) {
        Map<String, Object> mapSysInfo = GsonUtil.convertJsonToMap(sysInfoJsonPath);
        if (!mapSysInfo.containsKey(KEY_POSTGRES) || !mapSysInfo.containsKey(KEY_CS_NODES)
                || !mapSysInfo.containsKey(KEY_APP_NODES)) {
            logger.error("Invalid system info JSON!");  // FIXME
            throw new RuntimeException("Invalid system info JSON!");
        }
        
        Map<String, Object> dbInfo = (Map<String, Object>) mapSysInfo.get(KEY_POSTGRES);
        List<Map<String, Object>> csInfo = (List<Map<String, Object>>) mapSysInfo.get(KEY_CS_NODES);
        List<Map<String, Object>> appInfo = (List<Map<String, Object>>) mapSysInfo.get(KEY_APP_NODES);
        
        List<Host> installedHosts = resolveAllInstalledHosts(appInfo);
        List<Alert> installedAlerts = resolveAllInstalledAlerts(csInfo);
        
        // TODO: get installed templates
        // ...........
        
        // TODO: get installed resources
        // ...........
        
        StringBuilder errSummary = new StringBuilder();
        for (Component comp : expectedComponents) {
            StringBuilder errInfo4Component = new StringBuilder();
            
            // verify hosts and raise errors 
            errInfo4Component.append(HostValidator.validate(comp.getHosts(), installedHosts));
            // TODO: verify alerts and raise errors 
            errInfo4Component.append(AlertValidator.validate(comp.getAlerts(), installedAlerts));
            // TODO: verify templates and raise errors
            // ...........
            // TODO: verify resources and raise errors
            // ...........
            
            if (errInfo4Component.length() > 0) {
                ErrorInfoHelper.addHeader2ErrorInfo(errInfo4Component, comp.getPath());
                errSummary.append(errInfo4Component);
                errSummary.append(System.lineSeparator());
            }
        }
        
        // TODO: output the error report properly
        // ..........
    }
    
    private static List<Host> resolveAllInstalledHosts(List<Map<String, Object>> appInfo) {
        List<Host> installedHosts = new ArrayList<Host>();
        
        // resolve all the hosts in local system:
        installedHosts.addAll(HostResolver.resoveHost(CommonUtils.HOST_FILE));
        
        // resolve all the hosts in other APP nodes:
        for (Map<String, Object> app : appInfo) {
            List<Host> hosts = HostResolver.resoveRemoteHost(
                    CommonUtils.nullSafeToString(app.get("ip")),
                    CommonUtils.nullSafeToString(app.get("user")),
                    CommonUtils.nullSafeToString(app.get("password")));
            installedHosts.addAll(hosts);
        }
        
        return installedHosts;
    }
    
    private static List<Alert> resolveAllInstalledAlerts(List<Map<String, Object>> csInfo) {
        List<Alert> installedAlerts = new ArrayList<Alert>();
        
        // FIXME: only verify alerts on the first CS node for now.
        Map<String, Object> cs = csInfo.get(0);
        installedAlerts.addAll(AlertResolver.resolveAlertsFromCsNode(
                    CommonUtils.nullSafeToString(cs.get("ip")),
                    CommonUtils.nullSafeToString(cs.get("user")),
                    CommonUtils.nullSafeToString(cs.get("password"))));
        
        return installedAlerts;
    }
}
