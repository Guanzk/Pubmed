package com.searchproject.pubmed.util;

import com.searchproject.pubmed.dao.RedisDao;
import com.searchproject.pubmed.grpc.ProductSearchGrpc;
import com.searchproject.pubmed.service.searchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisTimer {
    @Autowired
    RedisDao redisDao;
    @Scheduled(cron = "0/10 * * * * *")
    public void timer(){
        log.debug("redis 心跳");
        redisDao.getAidSet("martin katz");
        redisDao.getPmidSet("human k5 keratin gene");
    }
//    @Autowired
//    ProductSearchGrpc.ProductSearchBlockingStub productSearchBlockingStub;
//    @Scheduled(cron = "0/50 * * * * *")
//    public void grpcTimer(){
//        log.debug("grpc 心跳");
//       productSearchBlockingStub.search()
//    }
}
