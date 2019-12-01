package DAO;

import Bean.AuthorInformation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        res=(HashMap<String, Integer>) ReadArticleFromMySQL.getAffiliationsTotalPapers(new LinkedList<String>(){{
            addAll(keySet);
        }});
        return res;
    }

    public static HashMap<String, List<String>> getPmidByAid(List<AuthorInformation> relatedAuthors) {
        HashMap<String, List<String>> res=new HashMap<>();
        for(AuthorInformation a:relatedAuthors){
            res.put(a.getAid(),new LinkedList<String>(){{
                addAll(ReadDataFromRedis.getPMIDfromaid(redis,a.getAid()));
            }});
        }
        return res;
    }

    public static HashMap<String, Integer> getPmidCitation(List<String> pmids) {
        HashMap<String, Integer> res=new HashMap<>();
        for(String pmid:pmids){
            String ref=ReadDataFromRedis.getReferencefromPMID(redis,pmid);
            res.put(pmid,ref==null?0:Integer.parseInt(ref));
        }

        return res;
    }
}
