package com.verify.main.verifyobjs;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.serializer.DefaultSerializer;

import com.verify.main.MockUtils;
import com.verify.main.TestUtils;

public class ComponentTest {

    @Test
    public void test() throws Exception {
        List<Component> cmps = mockCompListData();
        DefaultSerializer serializer = new DefaultSerializer();
        
        File outputFolder = TestUtils.getTestOutputFolder();
        File dataFile = new File(outputFolder, "components.dat");
        OutputStream os = new BufferedOutputStream(new FileOutputStream(dataFile));
        serializer.serialize(cmps, os);
        IOUtils.closeQuietly(os);
        assertTrue("No Exception Found." != null);
    }
    
    public List<Component> mockCompListData() {
        int i = 0;
        List<Component> components = new ArrayList<Component>();
        
        Component cmp = MockUtils.mockEmptyComponent("Base", "/BASE");
        List<Host> hosts = new ArrayList<Host>();
        List<Template> templates = new ArrayList<Template>();
        List<Alert> alerts = new ArrayList<Alert>();
        for (int j = 0; j < 5; j++) {
            i++;
            hosts.add(MockUtils.mockOneHost("name" + i));
            templates.add(MockUtils.mockOneTemplate("name"+i, String.valueOf(i), "00000"+i));
            alerts.add(MockUtils.mockOneAlert("name"+i, "pattern"+i, i, i*2, i*3, i*2, i*3));
        }
        cmp.setHosts(hosts);
        cmp.setTemplates(templates);
        cmp.setAlerts(alerts);
        components.add(cmp);
        
        cmp = MockUtils.mockEmptyComponent("Fabrix", "/DEVICES/CDN/FABRIX");
        hosts = new ArrayList<Host>();
        templates = new ArrayList<Template>();
        alerts = new ArrayList<Alert>();
        for (int j = 0; j < 5; j++) {
            i++;
            hosts.add(MockUtils.mockOneHost("name" + i));
            templates.add(MockUtils.mockOneTemplate("name"+i, String.valueOf(i), "00000"+i));
            alerts.add(MockUtils.mockOneAlert("name"+i, "pattern"+i, i, i*2, i*3, i*2, i*3));
        }
        cmp.setHosts(hosts);
        cmp.setTemplates(templates);
        cmp.setAlerts(alerts);
        components.add(cmp);
        
        cmp = MockUtils.mockEmptyComponent("AMS", "/DOWNSTREAMS/AMS");
        hosts = new ArrayList<Host>();
        templates = new ArrayList<Template>();
        alerts = new ArrayList<Alert>();
        for (int j = 0; j < 5; j++) {
            i++;
            hosts.add(MockUtils.mockOneHost("name" + i));
            templates.add(MockUtils.mockOneTemplate("name"+i, String.valueOf(i), "00000"+i));
            alerts.add(MockUtils.mockOneAlert("name"+i, "pattern"+i, i, i*2, i*3, i*2, i*3));
        }
        cmp.setHosts(hosts);
        cmp.setTemplates(templates);
        cmp.setAlerts(alerts);
        components.add(cmp);
        
        return components;
    }

}
