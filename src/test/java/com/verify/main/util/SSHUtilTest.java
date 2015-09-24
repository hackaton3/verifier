package com.verify.main.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.verify.main.TestUtils;

public class SSHUtilTest {
    private static final String TEST_HOST = "10.116.54.21";
    private static final String TEST_USR = "root";
    private static final String TEST_PWD = "root1234";
    
    private static final String RMT_ALERT_CONF = "/etc/logstash/alerts.yaml";

    @Test
    public void testGetFile() throws Exception {
        File outputFolder = TestUtils.getTestOutputFolder();
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
        assertNotNull("No Exception Found.");
    }

}
