package com.verify.main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.json.GsonJsonParser;

public class GsonUtil {

	public Map<String, Object> convertJsonToMap(String jsonPath){
		
		GsonJsonParser parser = new GsonJsonParser();
		try {
			String jsonStr = IOUtils.toString(new FileInputStream(new File(jsonPath)));
			Map<String, Object> jsonMap = parser.parseMap(jsonStr);
			return jsonMap;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
