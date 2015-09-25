package com.verify.main.stardardgenerator;

import java.util.ArrayList;
import java.util.List;

import com.verify.main.util.MockUtils;
import com.verify.main.verifyobjs.Alert;
import com.verify.main.verifyobjs.Component;
import com.verify.main.verifyobjs.Host;
import com.verify.main.verifyobjs.Template;

public class StardardFileGenerator {

	public static List<Component> generateFile() {
		int i = 0;
		List<Component> components = new ArrayList<Component>();

		Component cmp = MockUtils.mockEmptyComponent("Base", "/BASE");
		List<Host> hosts = new ArrayList<Host>();
		List<Template> templates = new ArrayList<Template>();
		List<Alert> alerts = new ArrayList<Alert>();
//		for (int j = 0; j < 5; j++) {
//			i++;
//			hosts.add(MockUtils.mockOneHost("name" + i));
//			templates.add(MockUtils.mockOneTemplate("name" + i, String.valueOf(i), "00000" + i));
//			alerts.add(MockUtils.mockOneAlert("name" + i, "pattern" + i, i, i * 2, i * 3, i * 2, i * 3));
//		}
		templates.add(MockUtils.mockOneTemplate("Calculate Content File Checksum", "1", ""));
		templates.add(MockUtils.mockOneTemplate("Create Or Update Title", "9", ""));
		templates.add(MockUtils.mockOneTemplate("Handle Asset Reprocessing", "4", ""));
		templates.add(MockUtils.mockOneTemplate("Handle Asset Without Content", "7", "00000" + i));
		templates.add(MockUtils.mockOneTemplate("Handle Content File Ingestion", "3", ""));
		templates.add(MockUtils.mockOneTemplate("Handle Delete Verb", "1", ""));
		templates.add(MockUtils.mockOneTemplate("Handle Metadata Validation", "6", ""));
		templates.add(MockUtils.mockOneTemplate("Handle Metadata Version Control", "1", ""));
		templates.add(MockUtils.mockOneTemplate("Ingest Common Template", "56", "120112"));
		templates.add(MockUtils.mockOneTemplate("Ingest Common Template", "56", "AD0101"));
		templates.add(MockUtils.mockOneTemplate("Ingest Common Template", "56", "CM0155"));
		templates.add(MockUtils.mockOneTemplate("Parse Ingestion File", "6", ""));
		templates.add(MockUtils.mockOneTemplate("Validate Checksums By Configuration", "5", ""));
		templates.add(MockUtils.mockOneTemplate("Validate Metadata", "1", ""));
		
		templates.add(MockUtils.mockOneTemplate("Validate Metadata12312", "1", ""));
		
		hosts.add(MockUtils.mockOneHost("dbserver"));
		hosts.add(MockUtils.mockOneHost("ACSServer"));
		hosts.add(MockUtils.mockOneHost("watchpoint-app"));
		hosts.add(MockUtils.mockOneHost("Heavy_Use_FMS"));
		hosts.add(MockUtils.mockOneHost("Metadata_FMS"));
		
		for (int j = 0; j < 5; j++) {
			i++;
			templates.add(MockUtils.mockOneTemplate("name" + i, String.valueOf(i), "00000" + i));
			alerts.add(MockUtils.mockOneAlert("name" + i, "pattern" + i, i, i * 2, i * 3, i * 2, i * 3));
		}
		cmp.setHosts(hosts);
		cmp.setTemplates(templates);
		cmp.setAlerts(alerts);
		components.add(cmp);

		cmp = MockUtils.mockEmptyComponent("Fabrix", "/DEVICES/CDN/FABRIX");
		hosts = new ArrayList<Host>();
		hosts.add(MockUtils.mockOneHost("fabrix"));
		hosts.add(MockUtils.mockOneHost("fabrix-additional"));
		
		templates = new ArrayList<Template>();
		alerts = new ArrayList<Alert>();
//		for (int j = 0; j < 5; j++) {
//			i++;
//			hosts.add(MockUtils.mockOneHost("name" + i));
//			templates.add(MockUtils.mockOneTemplate("name" + i, String.valueOf(i), "00000" + i));
//			alerts.add(MockUtils.mockOneAlert("name" + i, "pattern" + i, i, i * 2, i * 3, i * 2, i * 3));
//		}
		templates.add(MockUtils.mockOneTemplate("Validate Manifest", "1", ""));
		
		cmp.setHosts(hosts);
		cmp.setTemplates(templates);
		cmp.setAlerts(alerts);
		components.add(cmp);

		cmp = MockUtils.mockEmptyComponent("AMS", "/DOWNSTREAMS/AMS");
		hosts = new ArrayList<Host>();
		hosts.add(MockUtils.mockOneHost("Package"));
		hosts.add(MockUtils.mockOneHost("NameServer"));
		hosts.add(MockUtils.mockOneHost("PropagationDirector"));
		
		
		templates = new ArrayList<Template>();
		alerts = new ArrayList<Alert>();
		cmp.setHosts(hosts);
		cmp.setTemplates(templates);
		cmp.setAlerts(alerts);
		components.add(cmp);

		return components;

	}
}
