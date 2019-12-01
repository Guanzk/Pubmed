package Server;

import Bean.*;
import DAO.ReadAuthorFromMySQL;
import DAO.ReadDataFromRedis;
import Services.PubmedSearch;
import Util.MakeJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PubmedSearchImpl implements PubmedSearch {
    Logger log= LoggerFactory.getLogger(PubmedSearchImpl.class);
    @Override
    public QueryResponse processPubmedSearch(String query) {
        log.info("———MessageReseived Function Process—-——————-");
        long startTime=System.currentTimeMillis();
        log.info("Query:"+query);
        query=query.toLowerCase();
        Author author=new Author();
        ReadDataFromRedis redis=new ReadDataFromRedis();
        Set<String> aid= ReadDataFromRedis.getAidSetFromFullname(redis,query);
        Set<String> entityPMIDS= ReadDataFromRedis.getPMIDfromEntity_Pool(redis,query);
        QueryResponse response=new QueryResponse();

        if(aid.size()>0){
            log.info("aids:"+aid);//同名策略，待扩充
            List<String>aids=new ArrayList<>();
            aids.addAll(aid);
            aids.remove("0");
//            String id="0";
            AuthorInformation usefulAuthor= ReadAuthorFromMySQL.findAuthorByAids(aids);
//            for(int i=0;i<aids.size();i++){
//                if(!aids.get(i).equals("0")){
//                    id=aids.get(i);
//                    break;
//                }
////            }
//            if(id.equals("0")){
//                response.setResult("not found author");
//            }else{
//                log.info("search:"+id);
//                author=QueryResult.processAuhtor(id);
//                String authorJson= MakeJson.makeAuthorJson(author);
//                log.info(authorJson);
//                response.setResult(authorJson);
//            }
            if(usefulAuthor==null){
                response.setResult("not found author");
            }else{
                log.info("search:"+usefulAuthor.getAid());
                author=QueryResult.processAuhtor(usefulAuthor.getAid());
                String authorJson= MakeJson.makeAuthorJson(author);
                log.info(authorJson);
                response.setResult(authorJson);
            }

        }else if(!entityPMIDS.isEmpty()){

            MedEntity entity=QueryResult.processMedEntity(query);
            String entityJson=MakeJson.makeEntityJson(entity);
            log.info("entity json:"+entityJson);
            response.setResult(entityJson);

        }
        else if(aid==null){
            log.info("can not find query");
            response.setSearchType("null");
            response.setResult("not found query");
        }
        long finishQueryTime=System.currentTimeMillis();
        log.info("process time:"+(finishQueryTime-startTime));

        return response;
    }
    private HashMap<String,Integer>pubyearCount(List<Article> articles){
        HashMap<String,Integer>res=new HashMap<>();
        for(Article article:articles){
            String pubYear=article.getPubyear();
            if(res.containsKey(pubYear)){
                res.put(pubYear,res.get(pubYear)+1);
            }else{
                res.put(pubYear,1);
            }
        }
        return res;
    }
}
