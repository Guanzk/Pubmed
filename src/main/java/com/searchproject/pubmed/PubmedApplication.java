package com.searchproject.pubmed;

//import com.searchproject.pubmed.config.MySQLConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@SpringBootApplication
public class PubmedApplication {

    public static void main(String[] args) {
//        File file = null;
//        try {
//            file = ResourceUtils.getFile("classpath:mysql.conf");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println(file.exists());
//        try {
//            MySQLConfig.load(MySQLConfig.class.getClassLoader().getResource("mysql.conf").getFile());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        SpringApplication.run(PubmedApplication.class, args);
    }

}
