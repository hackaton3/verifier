/**
 * 
 */
package com.verify.main.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.verify.main.verifyobjs.Resourse;

/**
 * @author Simon
 * 
 */
public class ResourseResolver {

	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * @return
	 */
	public List<Resourse> getResourceFromDatabase() {
		PGPoolingDataSource source = new PGPoolingDataSource();
		source.setDataSourceName("verify");
		source.setServerName("10.116.54.14");
		source.setPortNumber(5444);
		source.setDatabaseName("ttv");
		source.setUser("wfs");
		source.setPassword("Wf$1234");
		source.setMaxConnections(3);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(source);
		
		return jdbcTemplate.query(
				"select t.connectionstring, t.heartbeatfrequency, t.heartbeatconnectionstring, t.maxconcurrentusers, t.name,trt.name as resourceTypename, tg.name as tgname  from ttv_resource t "
				+ "left join ttv_resourcetype trt on t.resourcetypeid = trt.resourcetypeid left join ttv_resourcegroup tg on trt.resourcetypeid = tg.resourcetypeid",    
	                new RowMapper<Resourse>(){  
	                    @Override  
	                    public Resourse mapRow(ResultSet rs, int rowNum) throws SQLException {  
	                    	Resourse r  = new Resourse();  
	                    	r.setConnectionString(rs.getString("connectionstring"));
	                    	r.setHeartbeatFrequency(rs.getInt("heartbeatfrequency"));
	                    	r.setHeartbeatString(rs.getString("heartbeatconnectionstring"));
	                    	r.setMaxConcurrency(rs.getInt("maxconcurrentusers"));
	                    	r.setName(rs.getString("name"));
	                    	r.setResourceGroup(rs.getString("tgname"));
	                    	r.setResourceType(rs.getString("resourceTypename"));
	                        return r;  
	                    }  
	        });  
	}
}
