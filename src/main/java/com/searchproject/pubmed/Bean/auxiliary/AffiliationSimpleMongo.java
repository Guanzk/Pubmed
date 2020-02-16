package com.searchproject.pubmed.Bean.auxiliary;

import lombok.Data;
import org.bson.types.Binary;

import java.util.ArrayList;
import java.util.List;

@Data
public class AffiliationSimpleMongo {
    private List<Binary> authors;
    //线程不安全
    private List<String>authorsString=null;

    public List<String> getAuthors(){
        if(authorsString==null){
            authorsString=new ArrayList<>();
            for(Binary data:authors){
                authorsString.add(new String(data.getData()));
            }
        }
        return authorsString;
    }
    private String affiliation;
}
