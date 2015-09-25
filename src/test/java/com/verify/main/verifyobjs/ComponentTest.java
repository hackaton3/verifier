package com.verify.main.verifyobjs;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.core.serializer.DefaultSerializer;

import com.verify.main.TestUtils;
import com.verify.main.stardardgenerator.StardardFileGenerator;

public class ComponentTest {

    @Test
    public void testSerialize() throws Exception {
        List<Component> cmps = StardardFileGenerator.generateFile();
        DefaultSerializer serializer = new DefaultSerializer();
        
        File outputFolder = TestUtils.getTestOutputFolder();
        File dataFile = new File(outputFolder, "components.dat");
        OutputStream os = new BufferedOutputStream(new FileOutputStream(dataFile));
        serializer.serialize(cmps, os);
        IOUtils.closeQuietly(os);
        assertNotNull("No Exception Found.");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeserialize() throws Exception {
        DefaultDeserializer deserializer = new DefaultDeserializer();
        InputStream is = new BufferedInputStream(new ClassPathResource("SampleComponentList.dat").getInputStream());
        List<Component> cmps = (List<Component>) deserializer.deserialize(is);
        IOUtils.closeQuietly(is);
        assertNotNull(cmps);
        assertEquals(cmps.size(), 3);
    }
    
}
