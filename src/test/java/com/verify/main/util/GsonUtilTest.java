package com.verify.main.util;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.verify.main.service.VerifyService;

public class GsonUtilTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testConvertJsonToMap() throws Exception {
        String jsonPath = new ClassPathResource("install_sysInfo.json").getFile().getAbsolutePath();
        Map<String, Object> mapSysInfo = GsonUtil.convertJsonToMap(jsonPath);
        assertNotNull(mapSysInfo);
        
        for (String key : mapSysInfo.keySet()) {
            System.out.println(key);
        }
        assertTrue(mapSysInfo.get(VerifyService.KEY_ORACLE) instanceof Map);
        assertTrue(mapSysInfo.get(VerifyService.KEY_POSTGRES) instanceof Map);
        assertTrue(mapSysInfo.get(VerifyService.KEY_CS_NODES) instanceof List);
        assertTrue(mapSysInfo.get(VerifyService.KEY_APP_NODES) instanceof List);
        
        List<Object> csInfo = (List<Object>) mapSysInfo.get(VerifyService.KEY_CS_NODES);
        for (Object cs : csInfo) {
            assertTrue(cs instanceof Map);
        }
        
        List<Object> appInfo = (List<Object>) mapSysInfo.get(VerifyService.KEY_APP_NODES);
        for (Object app : appInfo) {
            assertTrue(app instanceof Map);
        }
    }

}
