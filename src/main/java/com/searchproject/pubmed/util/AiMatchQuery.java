package com.searchproject.pubmed.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AiMatchQuery {
    @Autowired
    AiMapList aiMapList;

    public List<QueryClass> ngramMatch(String query) {
        List<QueryClass>rst=new ArrayList<>();
        if("".equals(query)==false){
            String[]input=query.split(" ");
            QueryClass qc=leftMax(input);
            String subSeq=qc.getMaxQuery();
            if("".equals(subSeq)==false){
                int len=subSeq.split(" ").length;
                for(int i=0;i<len;i++){
                    input=delete(0,input);
                }
                rst=ngramMatch(getArrString(input));
                rst.add(qc);
                return rst;
            }else{
                input=delete(0,input);
                rst=ngramMatch(getArrString(input));
                return rst;
            }
        }
        return rst;
    }

    private QueryClass leftMax(String[] input) {
        QueryClass qc=new QueryClass();
        qc.setMaxQuery("");
        qc.setQueryType("");
        while(input.length>0){
            String subSeq="";
            if(input.length<20){
                subSeq=getArrString(input);
            }else{
                for(int i=0;i<21;i++){
                    if(subSeq.equals("")){
                        subSeq=input[i];
                    }else {
                        subSeq=subSeq+" "+input[i];
                    }
                }
            }
            String qt=getQType(subSeq);
            if("".equals(qt)==false){
                qc.setQueryType(qt);
                qc.setMaxQuery(subSeq);
                return qc;
            }else if(subSeq.split(" ").length>1){
                input=delete(input.length-1,input);
            }else if(subSeq.split(" ").length==1){
                return qc;
            }
        }
        return qc;
    }

    private String[] delete(int index, String[] input) {
        //数组的删除其实就是覆盖前一位
        String[] arrNew = new String[input.length - 1];
        for (int i = index; i < input.length - 1; i++) {
            input[i] = input[i + 1];
        }
        System.arraycopy(input, 0, arrNew, 0, arrNew.length);
        return arrNew;
    }

    private String getQType(String subSeq) {
        String result="";
        if(aiMapList.getIndusSet().contains(subSeq)){
            result="Field";
        }else if(aiMapList.getKeywordSet().contains(subSeq)){
            result="KeyWord";
        }else if(aiMapList.getOrganizationSet().contains(subSeq)){
            result="Org";
        }else if(aiMapList.getExpertSet().contains(subSeq)){
            result="Expert";
        }
        return result;
    }

    private String getArrString(String[] input) {
        String str="";
        for(int i=0;i<input.length;i++){
            if(str.equals("")){
                str=input[i];
            }else{
                str=str+" "+input[i];
            }
        }
        return str;
    }
}
