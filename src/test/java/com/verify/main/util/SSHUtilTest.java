package com.verify.main.util;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class SSHUtilTest {
    private static final String TEST_HOST = "10.116.54.21";
    private static final String TEST_USR = "root";
    private static final String TEST_PWD = "root1234";
    
    private static final String RMT_ALERT_CONF = "/etc/logstash/alerts.yaml";
    
    private File createTestOutputFolder() throws Exception {
        URL testOutputUrl = new ClassPathResource("output").getURL();
        File outputFolder = new File(testOutputUrl.getFile());
        return outputFolder;
    }

    @Test
    public void testGetFile() throws Exception {
        File outputFolder = createTestOutputFolder();
        File localFile = new File(outputFolder, "alerts.yaml");
        
        SSHUtil util = new SSHUtil();
        util.setHost(TEST_HOST);
        util.setPassword(TEST_PWD);
        util.setUser(TEST_USR);
        
        util.getFile(RMT_ALERT_CONF, localFile.getAbsolutePath());
        assertNotNull(localFile);
        assertTrue(localFile.length() > 0);
    }
    
    @Test
    public void testRunCommand() throws Exception {
        SSHUtil util = new SSHUtil();
        util.setHost(TEST_HOST);
        util.setPassword(TEST_PWD);
        util.setUser(TEST_USR);
        util.runCommand("ls /home");
        assertTrue("No Exception Found." != null);
    }

}
