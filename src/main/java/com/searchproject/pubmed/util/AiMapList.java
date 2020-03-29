package com.searchproject.pubmed.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

@Data
@Slf4j
@Component
public class AiMapList {
    private    HashSet<String> expertSet;
    private  HashSet<String>organizationSet;
    private  HashSet<String>keywordSet;
    private  HashSet<String>indusSet;

    public AiMapList(){
        expertSet=createSet("new_author.txt");
        log.info("expert加载成功");
        organizationSet=createSet("new_organization.txt");
        log.info("organization加载成功");
        keywordSet=createSet("new_keyword.txt");
        log.info("keyword加载成功");
        indusSet=createSet("new_industry.txt");
        log.info("indus加载成功");

    }

    private static HashSet<String> createSet(String s) {
        InputStream is= null;
        try {
            is = new ClassPathResource(s).getInputStream();
        } catch (IOException e) {

            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        HashSet<String>set=new HashSet<>();
        String line;

            try {
               while (!((line=br.readLine())!=null)) {
                    set.add(line);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();


            }finally {

            }
            return set;

    }


}
