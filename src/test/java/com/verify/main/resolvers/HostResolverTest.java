/**
 * 
 */
package com.verify.main.resolvers;

import org.junit.Test;

/**
 * @author esimome
 *
 */
public class HostResolverTest {
	
	@Test
	public void testResolve() {
		String path = this.getClass().getClassLoader().getResource("hosts").getPath(); 
		System.out.println(path);
		HostResolver.resoveHost(path); 
	}
	
	@Test
	public void testResolveRemoteHost() {
		HostResolver.resoveRemoteHost("10.116.54.12", "root", "root1234"); 
	}
}
