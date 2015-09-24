package com.verify.main.installed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.serializer.DefaultDeserializer;

import com.verify.main.verifyobjs.Component;


public class ResolveInstalledComponents {
	
	private String INSTALLED_COMPONENTS_FILE = "/opt/tandbergtv/cms/prepack/installedcomponent.json"; //C:\Users\esijwen\Desktop\installedcomponent.json
	private List<Component> standardComponents;
	private List<Component> installedComponents = new ArrayList<Component>();
	
	public List<Component> resolve(String standFile) {
		loadStandardComponentList(standFile);
		loadInstalledComponents();
		return getExpectedInstalledComponentList();
	}
	
	@SuppressWarnings("unchecked")
	private void loadStandardComponentList(String standFile) {
		
        DefaultDeserializer deserializer = new DefaultDeserializer();
        InputStream is = null;
		try {
			is = new FileInputStream(new File(standFile));
			standardComponents = (List<Component>) deserializer.deserialize(is);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			IOUtils.closeQuietly(is);
		}
		
	}
	
	private void loadInstalledComponents() {


		ObjectMapper mapper = new ObjectMapper();
		ComponentJsonObject obj = null;
		try {
			obj = mapper.readValue(new File(INSTALLED_COMPONENTS_FILE), ComponentJsonObject.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if ("ROOT".equalsIgnoreCase(obj.getComponentName())) {
			loadComponentName(obj.subComponents, "");
		}
	}
	
	private void loadComponentName(List<ComponentJsonObject> subComponents, String path) {
		for (ComponentJsonObject sub : subComponents) {
			if (sub.subComponents == null) {
				Component component = new Component();
				component.setName(sub.componentName);
				component.setPath(path + "/" + sub.componentName);
				installedComponents.add(component);
			} else {
				loadComponentName(sub.subComponents, path + "/" + sub.componentName);
			}
			
		}
	}
	
	private List<Component> getExpectedInstalledComponentList() {
		List<Component> expectedInstalledComponentsName = new ArrayList<Component>();
		for (Component installed : installedComponents) {
			for (Component standard : standardComponents) {
				if (standard.getPath().equalsIgnoreCase(installed.getPath())) {
					expectedInstalledComponentsName.add(standard);
				}
			}
		}
		
		return expectedInstalledComponentsName;
		
	
	}
	
	public static void main(String[] args) {
		String standardFile = "C:\\CMS\\GiveMe5\\codes\\src\\test\\resources\\SampleComponentList.dat";
		standardFile = standardFile.replace("\\", "/");
		System.out.println(new ResolveInstalledComponents().resolve(standardFile));
	}

}
