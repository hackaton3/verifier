package com.verify.main;

import java.util.Properties;

import org.junit.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import junit.framework.Assert;

public class TestConvertYaml {
	
	@Test
	public void convertYaml(){
		System.out.println("Start xml conversion testing...");
		
		YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
		
		ClassPathResource resouce = new ClassPathResource("yaml_test.txt");
		
		yamlFactory.setResources(resouce);
		
		Properties pro = yamlFactory.getObject();
		
		String url = pro.getProperty("environments.dev.url");
		
		Assert.assertEquals("failed", "http://dev.bar.com", url);
	}
}
