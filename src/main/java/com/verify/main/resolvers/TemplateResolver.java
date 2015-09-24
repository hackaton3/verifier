package com.verify.main.resolvers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.verify.main.util.GsonUtil;
import com.verify.main.verifyobjs.Template;


public class TemplateResolver {
	
	private JdbcTemplate tempalte;
	
	public JdbcTemplate getTempalte() {
		return tempalte;
	}

	public void setTempalte(JdbcTemplate tempalte) {
		this.tempalte = tempalte;
	}


	private String SQL_GET_TEMPALTE = "select id_, version_, selectionkey from jbpm_processdefinition left join ttv_selector on id_ = processdefinitionid where processdefinitiontypeid = 2";
	
	public List<Template> resolveActualTemplate(){
		
		List<Template> tempaltes = tempalte.queryForList(SQL_GET_TEMPALTE, null, Template.class);
		
		return tempaltes;
		
	}
	
	public List<Template> resolveExpectedTemplate(String rootPath) throws IOException{
//		String jsonConfig = rootPath + 
//		
//		Map<String, Object> jsonMap = GsonUtil.convertJsonToMap(jsonConfig);
//		
//		@SuppressWarnings("unchecked")
//		List<String> rpmList =  (ArrayList<String>) jsonMap.get("templateRpms");
//		
//		String rpmPath = "";
//		
//		for(String templateName : rpmList){
//			
//		}
		
		
		return null;
	}
	
	
	public static void main(String[] args){
		
		try {
			new TemplateResolver().resolveExpectedTemplate("/home/cms/workspace_updated/hackathon_repo/verifier/src/test/resources/config.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
