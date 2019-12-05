package test;

import DAO.AuthorUtil;
import DAO.ReadDataFromRedis;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
public class TestRedis {
    public static void main(String[] args) {
        String aid="3701603";
//        ReadDataFromRedis redis=new ReadDataFromRedis();
//        Set<String> pmids=ReadDataFromRedis.GetPMIDfromaid(redis,"3701603");
        ReadDataFromRedis redis=new ReadDataFromRedis();
        Set<String> aids= ReadDataFromRedis.getAidSetFromFullname(redis,"t hayashi");
//        AuthorInformation authorInformation= AuthorUtil.getAuthorInformation(aid);
        List<String> pmids=AuthorUtil.getPmids(aid);
        log.info(aids.toArray().toString());
        log.info(pmids.toArray().toString());
        System.out.println("aids:"+aids);

    }
}
