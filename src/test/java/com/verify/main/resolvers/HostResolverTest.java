/**
 * 
 */
package com.verify.main.resolvers;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.verify.main.verifyobjs.Host;

/**
 * @author esimome
 *
 */
public class HostResolverTest {
	
	@Test
	public void testResolve() {
		String path = this.getClass().getClassLoader().getResource("hosts").getPath(); 
		List<Host> hosts  = HostResolver.resoveHost(path); 
		Assert.assertEquals(19, hosts.size());
		System.out.println(hosts);
	}
	
	@Test
	public void testResolveRemoteHost() {
		List<Host> hosts = HostResolver.resoveRemoteHost("10.116.54.12", "root", "root1234"); 
		Assert.assertTrue(!hosts.isEmpty());
		//Assert.assertEquals(19, hosts.size());
		//System.out.println(hosts);
	}
}
