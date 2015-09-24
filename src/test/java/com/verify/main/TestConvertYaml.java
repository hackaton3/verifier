package com.verify.main;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import com.verify.main.util.CommonUtils;

import static org.junit.Assert.*;

public class TestConvertYaml {
	@Test
	@Ignore
	public void convertYaml(){
		System.out.println("Start yaml conversion testing...");
		
		YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
		
		ClassPathResource resouce = new ClassPathResource("yaml_test.txt");
		
		yamlFactory.setResources(resouce);
		
		Properties pro = yamlFactory.getObject();
		
		String url = pro.getProperty("environments.dev.url");
		
		assertEquals("failed", "http://dev.bar.com", url);
	}
	
    @Test
    public void testAlertYamlUsingProperty() {
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setResources(new ClassPathResource("alerts.yaml"));
        Properties propAlerts = yamlFactory.getObject();
        assertNotNull(propAlerts);
        for (Object key : propAlerts.keySet()) {
            System.out.println(key);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAlertYamlUsingMap() {
        YamlMapFactoryBean yamlFactory = new YamlMapFactoryBean();
        yamlFactory.setResources(new ClassPathResource("alerts.yaml"));
        Map<String, Object> rootAlert = yamlFactory.getObject();
        assertNotNull(rootAlert);
        
        Object firstEle = rootAlert.get(CommonUtils.ROOT_ELEM_NAME_4YML);
        assertNotNull(firstEle);
        System.out.println(firstEle);
        assertTrue(firstEle instanceof List);
        
        List<Map<String, Object>> firstList = (List<Map<String, Object>>) firstEle;
        for (Map<String, Object> fmap : firstList) {
            for (Object key : fmap.keySet()) {
                System.out.println(key);
            }
        }
    }
}
