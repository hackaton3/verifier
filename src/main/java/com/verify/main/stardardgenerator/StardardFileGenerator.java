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
		for (int j = 0; j < 5; j++) {
			i++;
			templates.add(MockUtils.mockOneTemplate("name" + i, String.valueOf(i), "00000" + i));
			alerts.add(MockUtils.mockOneAlert("name" + i, "pattern" + i, i, i * 2, i * 3, i * 2, i * 3));
		}
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
		templates.add(MockUtils.mockOneTemplate("name" + i, String.valueOf(i), "00000" + i));
		alerts = new ArrayList<Alert>();
		for (int j = 0; j < 5; j++) {
			i++;
			templates.add(MockUtils.mockOneTemplate("name" + i, String.valueOf(i), "00000" + i));
			alerts.add(MockUtils.mockOneAlert("name" + i, "pattern" + i, i, i * 2, i * 3, i * 2, i * 3));
		}
		cmp.setHosts(hosts);
		cmp.setTemplates(templates);
		cmp.setAlerts(alerts);
		components.add(cmp);

		return components;

	}
}
