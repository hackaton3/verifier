package com.verify.main.resolvers;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.verify.main.util.SSHUtil;
import com.verify.main.verifyobjs.Template;

public class TemplateResolverTest {

	@Test
	public void testResolveActualTemplate() {
		TemplateResolver resolver = new TemplateResolver();
		List<Template> templates = null;
		try {
			templates = resolver.resolveActualTemplate("10.116.54.14", "ttv", "wfs", "Wf$1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(templates != null);
		Assert.assertTrue(templates.size() > 0);
		
	}
	
	@Test
	public void testUnpackRpm(){
		SSHUtil util = new SSHUtil();
		util.setHost("10.116.54.22");
		util.setUser("root");
		util.setPassword("root1234");
		
		util.runCommand("cd /root/prepack-aio-3.9.000.016/rpms/; rpm2cpio /root/prepack-aio-3.9.000.016/rpms/ingest-common-workflows-3.9.000.001.rpm | cpio -div");
//		util.runCommand("cp /root/prepack-aio-3.9.000.016/rpms/ingest-common-workflows-3.9.000.001.rpm /tmp/ingest-common-workflows-3.9.000.001.rpm");
	}

}
