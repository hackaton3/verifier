package com.verify.main.verifyobjs;

import static org.junit.Assert.*;

import org.junit.Test;

public class HostTest {

    @Test
    public void testToString() {
        Host host = new Host();
        host.setName("name01");
        System.out.println(host.toString());
        assertNotNull("No Exception Found.");
    }

}
