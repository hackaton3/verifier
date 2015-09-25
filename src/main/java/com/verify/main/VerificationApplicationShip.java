package com.verify.main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.serializer.DefaultSerializer;

import com.verify.main.stardardgenerator.StardardFileGenerator;
import com.verify.main.verifyobjs.Component;


@SpringBootApplication
public class VerificationApplicationShip implements CommandLineRunner{
	private static final String OUTPUT_FILE_NAME = "components.dat";
    private static final String ARG_SHIP = "--ship";
    private static final Pattern ARG_OUTPUT_FOLDER = Pattern.compile("--ws-root=(\\S+)");
    

	@Override
	public void run(String... arg0) throws Exception {
		String destinationFilePath = null;
		if (arg0.length == 1) {
			destinationFilePath = arg0[0];
		}
		
		if (StringUtils.isBlank(destinationFilePath)) {
			return;
		}
		
		Matcher matcher = ARG_OUTPUT_FOLDER.matcher(destinationFilePath);
		while(matcher.find()) {
			destinationFilePath = matcher.group(1);
		}
		
		destinationFilePath = destinationFilePath + File.separator + OUTPUT_FILE_NAME;
		generatorFile(destinationFilePath);
		
	}
	
	
	private void generatorFile(String outputPath) {
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
	
	
	public static void main(String[] args) {
		if (StringUtils.isNotBlank(args[0]) && ARG_SHIP.equalsIgnoreCase(args[0])) {
			
			SpringApplication.run(VerificationApplicationShip.class, args[1]);
		}
	}

}
