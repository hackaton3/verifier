package com.verify.main.resolvers;

import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class TemplateResolverTest {

	@Test
	public void testResolveActualTemplate() {
		TemplateResolver resolver = new TemplateResolver();
		try {
			resolver.setTempalte(getJdbcTemplate());
			resolver.resolveActualTemplate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private JdbcTemplate getJdbcTemplate() throws SQLException{
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setURL("jdbc:oracle:thin:@172.16.8.50:1521/ttv");
		dataSource.setUser("epgmanager");
		dataSource.setPassword("epgmanager");
		JdbcTemplate tempalte = new JdbcTemplate(dataSource);
		return tempalte;
	}

}
