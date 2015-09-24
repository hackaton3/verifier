package com.verify.main.resolvers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.verify.main.util.GsonUtil;
import com.verify.main.verifyobjs.Template;


public class TemplateResolver {
	
	private JdbcTemplate tempalte;
	
	private String SQL_GET_TEMPALTE = "select name_ as name, version_ as version, selectionkey as selectorKey from jbpm_processdefinition left join ttv_selector on id_ = processdefinitionid where processdefinitiontypeid = 2";
	
	private static String JSON_PATH = "/components/BASE";
	
	private static String TEMPLATE_RPM_PATH = "rpms";
	
	public List<Template> resolveActualTemplate(String ip, String dataBaseName,  String userName, String password){
		
		
		JdbcTemplate tempalte = new JdbcTemplate(getDataSource(ip, dataBaseName, userName, password));
		
		List<Map<String, Object>> templateMap = tempalte.queryForList(SQL_GET_TEMPALTE);
		List<Template> templates = new ArrayList<Template>() ;
		
		for(Map<String, Object> t : templateMap){
			Template template = new Template();
			template.setName((String)t.get("name"));
			template.setVersion(((BigDecimal)t.get("version")).toString());
			template.setSelectorKey((String)t.get("selectorKey"));
			templates.add(template);
		}
		return templates;
		
	}
	
	public List<Template> resolveExpectedTemplate(String rootPath) throws IOException{
		String jsonConfigPath = rootPath + JSON_PATH;
		String templatePath = rootPath + TEMPLATE_RPM_PATH;
		
		Map<String, Object> jsonMap = GsonUtil.convertJsonToMap(jsonConfigPath);
		
		@SuppressWarnings("unchecked")
		List<String> rpmList =  (ArrayList<String>) jsonMap.get("templateRpms");
		
		String rpmPath = "";
		
		for(String templateName : rpmList){
			
		}
		
		
		return null;
	}
	
	private DataSource getDataSource(String ip, String dataBaseName,  String userName, String password){
		PGPoolingDataSource dataSource = new PGPoolingDataSource();
		dataSource.setServerName(ip);
		dataSource.setDatabaseName(dataBaseName);
		dataSource.setUser(userName);
		dataSource.setPortNumber(5444);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	public JdbcTemplate getTempalte() {
		return tempalte;
	}

	public void setTempalte(JdbcTemplate tempalte) {
		this.tempalte = tempalte;
	}
	
}
