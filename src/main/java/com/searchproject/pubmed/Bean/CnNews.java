package com.searchproject.pubmed.Bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "cnnews_tech_online")
@Entity
public class CnNews {
    @Id
    private String cnnewsid;
    private String title, release_time, author, remark, summary, tags, fn, pn, praise, source, url, comments;
    @Override
    public String toString(){
        String ans = "";
        ans+="id:"+this.getCnnewsid()+"title:"+this.getTitle()+"release_time:"+this.getRelease_time()+"author:"+this.getAuthor()+"remark:"+this.getRemark()+"summary:"+this.getSummary()+"tags:"+this.getTags()+"fn:"+this.getFn()+"pn:"+this.getPn()+"source:"+this.getSource()+"url:"+this.getUrl()+"comments:"+this.getComments();
        return ans;
    }
}
