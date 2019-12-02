package controller;

import Bean.QueryResponse;
import Services.PubmedSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthorController {
    @Autowired
    private PubmedSearch search;
    @RequestMapping(value = "/getAuthor")
    public String searchAuthor(String query){
        log.info("get query:"+query);
        QueryResponse response=search.processPubmedSearch(query);
        log.info("get the result:"+response.getResult());
        return response.getResult();
    }
}
