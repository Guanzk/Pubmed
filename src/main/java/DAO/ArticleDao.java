package DAO;

import Bean.AuthorInformation;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
public class ArticleDao {
    private static ReadDataFromRedis redis=new ReadDataFromRedis();
    public static HashMap<String, List<String>> getAffiliations(List<String> pmids) {
        HashMap<String,List<String>>res=new HashMap<>();
        for(String pmid:pmids){
            Set<String> affiliations=ReadDataFromRedis.getAffiliationfromPMID(redis,pmid);
            for(String affiliation:affiliations){
                if(!res.containsKey(affiliation)){
                    List<String>l=new LinkedList<>();
                    l.add(pmid);
                    res.put(affiliation,l);
                }else{
                    res.get(affiliation).add(pmid);
                }
            }
        }
        return res;
    }

    public static HashMap<String, Integer> getAffiliationTotalPapers(Set<String> keySet) {
        HashMap<String, Integer> res=new HashMap<>();
        long start=System.currentTimeMillis();
        res=(HashMap<String, Integer>) ReadArticleFromMySQL.getAffiliationsTotalPapers(new LinkedList<String>(){{
            addAll(keySet);
        }});
        long end=System.currentTimeMillis();
        log.debug("获取机构总文章数用时:"+(end-start));
        return res;
    }

    public static HashMap<String, List<String>> getPmidByAid(List<AuthorInformation> relatedAuthors) {
        HashMap<String, List<String>> res=new HashMap<>();
        long start=System.currentTimeMillis();
        for(AuthorInformation a:relatedAuthors){
            if(a.getAid().equals("0"))continue;//TODO 数据待优化，不然卡很久
            res.put(a.getAid(),new LinkedList<String>(){{
                addAll(ReadDataFromRedis.getPMIDfromaid(redis,a.getAid()));
            }});
        }
        long end=System.currentTimeMillis();
        log.debug("获取文章aid用时:"+(end-start));
        return res;
    }

    public static HashMap<String, Integer> getPmidCitation(List<String> pmids) {
        HashMap<String, Integer> res=new HashMap<>();
        long start=System.currentTimeMillis();
        for(String pmid:pmids){
            String ref=ReadDataFromRedis.getReferencefromPMID(redis,pmid);
            res.put(pmid,ref==null?0:Integer.parseInt(ref));
        }
        long end=System.currentTimeMillis();
        log.debug("获取文章引用用时:"+(end-start));
        return res;
    }
}
