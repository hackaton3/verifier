package com.verify.main.resolvers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.verify.main.util.GsonUtil;
import com.verify.main.util.SSHUtil;
import com.verify.main.util.XpathUtil;
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
	
	//TODO Need to figure out the uncertain path and xpath expression
	public List<Template> resolveExpectedTemplate(String rootPath) throws IOException{
		String jsonConfigPath = rootPath + JSON_PATH;
		String templatePath = rootPath + TEMPLATE_RPM_PATH;
		
		Map<String, Object> jsonMap = GsonUtil.convertJsonToMap(jsonConfigPath);
		
		@SuppressWarnings("unchecked")
		List<String> rpmList =  (ArrayList<String>) jsonMap.get("templateRpms");
		
		String unpackRpmCommand = "cd %s; rpm2cpio %s | cpio -div";
		String moveToTmp = "mv -r %s /tmp";
		String unpackParCommand = "cd /tmp/opt/t...; unzip xx.par";
		
		SSHUtil util = new SSHUtil();
		util.setHost("localhost");
		util.setUser("root");
		util.setPassword("root1234");
		
		List<Template> expectedList = new ArrayList<Template>();
		
		for(String templateName : rpmList){
			unpackRpmCommand = String.format(unpackRpmCommand, templatePath,  templateName);
			moveToTmp = String.format(moveToTmp, templatePath);
			util.runCommand(unpackRpmCommand);
			util.runCommand(moveToTmp);
			util.runCommand(unpackParCommand);
			
			Template tempalte = new Template();
			XpathUtil xpathUtil = new XpathUtil("/tmp/opt/.../xxx.jpdl");
			tempalte.setVersion(xpathUtil.getValue("//xxx/version").get(0));
			tempalte.setName(xpathUtil.getValue("//xxx/name").get(0));
			tempalte.setSelectorKey(xpathUtil.getValue("//xxx/selectKey").get(0));
			expectedList.add(tempalte);
			File dirToBeDel = new File("/tmp/opt");
			
			FileUtils.deleteDirectory(dirToBeDel);
		}
		
		return expectedList;
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
