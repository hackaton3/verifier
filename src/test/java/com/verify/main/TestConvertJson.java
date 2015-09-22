package com.verify.main;

import java.util.Map;

import org.junit.Test;
import org.springframework.boot.json.GsonJsonParser;

public class TestConvertJson {

	@Test
	public void testJsonConversion() {
		System.out.println("Start json conversion testing...");

		String json = "{\"name\":\"Duke\",\"address\":\"Menlo Park\",\"dateOfBirth\":\"Feb 1, 2000 12:00:00 AM\"}";

		GsonJsonParser parser = new GsonJsonParser();
		
		Map<String, Object> jsonMap = parser.parseMap(json);
		
		System.out.println(jsonMap.get("name"));
		System.out.println(jsonMap.get("address"));
		System.out.println(jsonMap.get("dateOfBirth"));
		

	}

}
