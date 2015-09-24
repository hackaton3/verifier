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

    @Test
    public void testEqualsTo_True() {
        Host host1 = new Host();
        host1.setName("name_eq");
        Host host2 = new Host();
        host2.setName("name_eq");
        assertEquals(host1, host2);
    }

    @Test
    public void testEqualsTo_False() {
        Host host1 = new Host();
        host1.setName("name1");
        Host host2 = new Host();
        host2.setName("name2");
        assertNotEquals(host1, host2);
    }

}
