package com.verify.main.util;

import com.verify.main.verifyobjs.Alert;
import com.verify.main.verifyobjs.Component;
import com.verify.main.verifyobjs.Host;
import com.verify.main.verifyobjs.Template;

public class MockUtils {
    public static Component mockEmptyComponent(String name, String path) {
        Component cmp = new Component();
        cmp.setName(name);
        cmp.setPath(path);
        return cmp;
    }
    
    public static Host mockOneHost(String name) {
        Host host = new Host();
        host.setName(name);
        return host;
    }
    
    public static Template mockOneTemplate(String name, String version, String selectorKey) {
        Template tpl = new Template();
        tpl.setName(name);
        tpl.setVersion(version);
        tpl.setSelectorKey(selectorKey);
        return tpl;
    }
    
    public static Alert mockOneAlert(String name, String pattern, int trap,
            int freqCount, int freqSecs, int threshCount, int threshSecs) {
        Alert alert = new Alert();
        alert.setName(name);
        alert.setPattern(pattern);
        alert.setTrap(trap);
        alert.setFreqCount(freqCount);
        alert.setFreqSecs(freqSecs);
        alert.setThreshCount(threshCount);
        alert.setThreshSeconds(threshSecs);
        return alert;
    }
}
