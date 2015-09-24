package com.verify.main.util;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DBUtils {

	public JdbcTemplate getJdbcTemplate() {
		PGPoolingDataSource source = new PGPoolingDataSource();
		source.setDataSourceName("verify");
		source.setServerName("10.116.54.14");
		source.setPortNumber(5444);
		source.setDatabaseName("ttv");
		source.setUser("wfs");
		source.setPassword("Wf$1234");
		source.setMaxConnections(3);
		return new JdbcTemplate(source);
	}
}
