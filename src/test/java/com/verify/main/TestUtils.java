package com.verify.main;

import java.io.File;
import java.net.URL;

import org.springframework.core.io.ClassPathResource;

public class TestUtils {
    public static File getTestOutputFolder() throws Exception {
        URL testOutputUrl = new ClassPathResource("output").getURL();
        File outputFolder = new File(testOutputUrl.getFile());
        return outputFolder;
    }
}
