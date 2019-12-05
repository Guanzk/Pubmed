package controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Redisapp {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String index(){
        return "Hello Spring";
    }
}
