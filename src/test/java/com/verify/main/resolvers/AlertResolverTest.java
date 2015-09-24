package com.verify.main.resolvers;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.verify.main.verifyobjs.Alert;

public class AlertResolverTest {

    @Test
    @Ignore("Not yet implemented")
    public void testResolveAlertsFromCsNode() {
        fail("Not yet implemented");
    }

    @Test
    public void testPopulateAlert_PureNumberTime() {
        Map<String, Object> mapNormalTime = constructAnAlertMap("Database Operation Failed",
                ".*SQLException.*", "15", "10", "30", "1", "10");
        Alert alertNormalTime = AlertResolver.populateAlert(mapNormalTime);
        assertEquals(alertNormalTime.getName(), "Database Operation Failed");
        assertEquals(alertNormalTime.getPattern(), ".*SQLException.*");
        assertEquals(alertNormalTime.getTrap(), 15);
        assertEquals(alertNormalTime.getFreqCount(), 10);
        assertEquals(alertNormalTime.getFreqSecs(), 30);
        assertEquals(alertNormalTime.getThreshCount(), 1);
        assertEquals(alertNormalTime.getThreshSeconds(), 10);
    }

    @Test
    public void testPopulateAlert_TimeWithColon() {
        Map<String, Object> mapNormalTime = constructAnAlertMap("Database Operation Failed",
                "(.*by:.*|.*BUSY.*)Error determining metadata path.*", "15", "10", "01:00:30", "1",
                "00:01:30");
        Alert alertNormalTime = AlertResolver.populateAlert(mapNormalTime);
        assertEquals(alertNormalTime.getName(), "Database Operation Failed");
        assertEquals(alertNormalTime.getPattern(),
                "(.*by:.*|.*BUSY.*)Error determining metadata path.*");
        assertEquals(alertNormalTime.getTrap(), 15);
        assertEquals(alertNormalTime.getFreqCount(), 10);
        assertEquals(alertNormalTime.getFreqSecs(), 3630);
        assertEquals(alertNormalTime.getThreshCount(), 1);
        assertEquals(alertNormalTime.getThreshSeconds(), 90);
    }
    
    private Map<String, Object> constructAnAlertMap(String name, String pattern, String trap,
            String freqCount, String freqTime, String threshCount, String threshTime) {
        Map<String, Object> mapAlert = new HashMap<String, Object>();
        mapAlert.put("name", name);
        mapAlert.put("pattern", pattern);
        mapAlert.put("trap", trap);
        
        Map<String, Object> mapFreq = new HashMap<String, Object>();
        mapFreq.put("count", freqCount);
        mapFreq.put("time", freqTime);
        mapAlert.put("frequency", mapFreq);
        
        Map<String, Object> mapThresh = new HashMap<String, Object>();
        mapThresh.put("count", threshCount);
        mapThresh.put("time", threshTime);
        mapAlert.put("threshold", mapThresh);
        
        return mapAlert;
    }

}
