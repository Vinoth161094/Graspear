package com.utilies;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile extends BaseClass {


    public static String Property() throws IOException {
        Properties pro = new Properties();

        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        pro.load(fis);

        String url = pro.getProperty("url");
        System.out.println("URL from properties file: " + url);

        fis.close();
        return url;
    }
}

