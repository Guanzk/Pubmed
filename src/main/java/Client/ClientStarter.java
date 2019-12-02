package Client;

import Bean.QueryResponse;
import Services.PubmedSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientStarter {
    private  static Logger log= LoggerFactory.getLogger(ClientStarter.class);
//数据问题，
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:rpc-invoke-config-client.xml");
        //php传输过来的数据格式： $data = $type_lower."==".$query."==".$page."==".$Seq."==".$port
        //way==t f christ==1==correlation==8280==info

//        String queryArgs=args;
////        log.info(queryArgs);
////        String query=args[0].split("==")[1];
//        String query="way==s s godbole==1==correlation==8280==info";
//        //t a trinh
//        query=query.split("==")[1];
//        System.out.println(query);
//        PubmedSearch search=(PubmedSearch) context.getBean("PubmedSearchImpl");
//        QueryResponse response=search.processPubmedSearch(query);
//        log.info("get the result:"+response.getResult());
//        System.out.println(response.getResult());
//        System.exit(0);
        //压力测试，稳定性，redis库，内存

    }
}
