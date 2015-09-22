package com.verify.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/bean-context.xml") 
public class ValidatorConfig {

}
