package com.verify.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.verify.main.config.ValidatorConfig;
import com.verify.main.installed.ResolveInstalledComponents;
import com.verify.main.service.VerifyService;
import com.verify.main.verifyobjs.Component;

@SpringBootApplication
public class VerificationApplication implements CommandLineRunner{
    
    private static final String ARG_VERIFY = "--verify";
    private static final Pattern ARG_INSTALL_PKG_ROOT = Pattern.compile("--install-pkg-root=(\\S+)");
    private static final Pattern ARG_EXPECT_INFO = Pattern.compile("--expect-info=(\\S+)");
    
    private static final String SYS_INFO_FILENAME = "install_sysInfo.json";
	
	@Autowired
	private VerifyService service;

    public static void main(String[] args) {
        SpringApplication.run(VerificationApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        boolean verify = false;
        String sysInfoJsonPath = "";
        String installPackagePath = "";
        String expectInfo = "";

        // extract arguments from command line
        for (String arg : args) {
            Matcher matPkgRoot = ARG_INSTALL_PKG_ROOT.matcher(arg);
            Matcher matExpInfo = ARG_EXPECT_INFO.matcher(arg);

            if (ARG_VERIFY.equals(arg)) {
                verify = true;
            } else if (matPkgRoot.find()) {
                installPackagePath = matPkgRoot.group(1);
                sysInfoJsonPath = installPackagePath + File.separator + SYS_INFO_FILENAME;
            } else if (matExpInfo.find()) {
                expectInfo = matExpInfo.group(1);
            }
        }

        if (verify) {
            // resolve installed component list
            ResolveInstalledComponents cmpResolver = new ResolveInstalledComponents();
            List<Component> components = cmpResolver.resolve(expectInfo);

            service.performVerify(components, sysInfoJsonPath);
        }
    }
	
	public void runTest(String... arg0) throws Exception {
        
//        service.performVerify();
        AnnotationConfigApplicationContext annotaionCtx = 
                  new AnnotationConfigApplicationContext();
        annotaionCtx.getEnvironment().setActiveProfiles("b");
        annotaionCtx.register(ValidatorConfig.class);
        annotaionCtx.refresh();
        
        System.out.println(annotaionCtx.getBean("lista", ArrayList.class));
//      System.out.println(annotaionCtx.getBean("listb", ArrayList.class));
    }
}
