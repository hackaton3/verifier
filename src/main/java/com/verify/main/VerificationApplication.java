package com.verify.main;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.verify.main.config.ValidatorConfig;
import com.verify.main.service.VerifyService;

@SpringBootApplication
public class VerificationApplication implements CommandLineRunner{
	
	@Autowired
	private VerifyService service;
	

    public static void main(String[] args) {
    	SpringApplication.run(VerificationApplication.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		
		service.performVerify();
		AnnotationConfigApplicationContext annotaionCtx = 
	              new AnnotationConfigApplicationContext();
		annotaionCtx.getEnvironment().setActiveProfiles("b");
		annotaionCtx.register(ValidatorConfig.class);
		annotaionCtx.refresh();
		
		System.out.println(annotaionCtx.getBean("lista", ArrayList.class));
//		System.out.println(annotaionCtx.getBean("listb", ArrayList.class));
	}	
}
