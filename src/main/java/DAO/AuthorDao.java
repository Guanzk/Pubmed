package DAO;

import Bean.AuthorInformation;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Tuple;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
@Slf4j
public class AuthorDao {
    private static ReadDataFromRedis redis=new ReadDataFromRedis();
    public static List<AuthorInformation> getEntityRelatedAuthors(List<String> pmids) {
        List<AuthorInformation>res=ReadAuthorFromMySQL.getAuthorsFromPmids(pmids);
        for(AuthorInformation a:res){
            if(a.getAid().equals("0")){
                continue;//Todo 优化数据，数据问题
            }
            Set<Tuple>s=ReadDataFromRedis.getKeywordsFromAid(redis,a.getAid());
            List<String>keywords=new LinkedList<>();
            for(Tuple t:s){
                keywords.add(t.getElement());
            }
            log.debug(a.getAid());
            a.setKeywords(keywords);
        }
        return res;
    }
}
