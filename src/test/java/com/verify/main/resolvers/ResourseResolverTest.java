package com.verify.main.resolvers;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.verify.main.verifyobjs.Resourse;

public class ResourseResolverTest {

    @Test
    public void testGetResourceFromDatabase() {
        List<Resourse> list = ResourseResolver.getResourceFromDatabase("10.116.54.14", "ttv", "wfs", "Wf$1234");
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

}
