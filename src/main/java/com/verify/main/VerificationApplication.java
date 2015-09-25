package com.verify.main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.serializer.DefaultSerializer;

import com.verify.main.config.ValidatorConfig;
import com.verify.main.installed.ResolveInstalledComponents;
import com.verify.main.service.VerifyService;
import com.verify.main.stardardgenerator.StardardFileGenerator;
import com.verify.main.verifyobjs.Component;

@SpringBootApplication
public class VerificationApplication implements CommandLineRunner{
    
    private static final String ARG_VERIFY = "--verify";
    private static final String ARG_SHIP = "--ship";
    private static final Pattern ARG_INSTALL_PKG_ROOT = Pattern.compile("--install-pkg-root=(\\S+)");
    private static final Pattern ARG_EXPECT_INFO = Pattern.compile("--expect-info=(\\S+)");
    private static final Pattern ARG_WORKSPACE_FOLDER = Pattern.compile("--ws-root=(\\S+)");
    
    private static final String OUTPUT_FILE_NAME = "components.dat";
    private static final String SYS_INFO_FILENAME = "install_sysInfo.json";
	
	@Autowired
	private VerifyService service;

    public static void main(String[] args) {
        SpringApplication.run(VerificationApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        boolean verifyFunction = false;
        boolean shipFunction = false;
        String sysInfoJsonPath = "";
        String installPackagePath = "";
        String expectInfo = "";
        String destinationFilePath = "";
        String workspaceRoot = "";

        // extract arguments from command line
        for (String arg : args) {
            Matcher matPkgRoot = ARG_INSTALL_PKG_ROOT.matcher(arg);
            Matcher matExpInfo = ARG_EXPECT_INFO.matcher(arg);
            Matcher matWorkspace = ARG_WORKSPACE_FOLDER.matcher(arg);

            if (ARG_VERIFY.equals(arg)) {
                verifyFunction = true;
            } else if (ARG_SHIP.equals(arg)) {
                shipFunction = true;
            } else if (matPkgRoot.find()) {
                installPackagePath = matPkgRoot.group(1);
                sysInfoJsonPath = installPackagePath + File.separator + SYS_INFO_FILENAME;
            } else if (matExpInfo.find()) {
                expectInfo = matExpInfo.group(1);
            } else if (matWorkspace.find()) {
                workspaceRoot = matWorkspace.group(1);
            }
        }

        if (verifyFunction) {
            // resolve installed component list
            ResolveInstalledComponents cmpResolver = new ResolveInstalledComponents();
            List<Component> components = cmpResolver.resolve(expectInfo);

            service.performVerify(components, sysInfoJsonPath);
        } else if (shipFunction) {
            destinationFilePath = OUTPUT_FILE_NAME;
            generatorFile(workspaceRoot, destinationFilePath);
        }
    }
    
    private void generatorFile(String workspaceRoot, String outputPath) {
        List<Component> cmps = StardardFileGenerator.generateFile();
        DefaultSerializer serializer = new DefaultSerializer();
        
        File dataFile = new File(outputPath);
        
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(dataFile));
            serializer.serialize(cmps, os);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
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
