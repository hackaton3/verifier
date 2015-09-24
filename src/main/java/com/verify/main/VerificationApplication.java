package com.verify.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.verify.main.config.ValidatorConfig;
import com.verify.main.service.VerifyService;
import com.verify.main.verifyobjs.Component;

@SpringBootApplication
public class VerificationApplication implements CommandLineRunner{
    
    private static final String ARG_VERIFY = "--verify";
    private static final Pattern ARG_INSTALL_PKG_ROOT = Pattern.compile("--install-pkg-root=(\\S+)");
    private static final Pattern ARG_EXPECT_INFO = Pattern.compile("--expect-info=(\\S+)");
	
	@Autowired
	private VerifyService service;

    public static void main(String[] args) {
        SpringApplication.run(VerificationApplication.class, args);
        
        // TODO: extract arguments from command line
        String sysInfoJsonPath = "";
        for (String arg : args) {
            
        }
        
        // TODO: get installed component list
        List<Component> components = new ArrayList<Component>();
        
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
