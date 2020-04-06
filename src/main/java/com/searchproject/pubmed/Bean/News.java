package com.searchproject.pubmed.Bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "news")
@Entity
public class News {
    @Id
    private int id;
    private String title, content, release_time, author, url;
    @Override
    public String toString(){
        String ans = "";
        ans+="id:"+this.getId()+"title:"+this.getTitle()+"content:"+this.getContent()+"release_time:"+this.getRelease_time()+"author:"+this.getAuthor()+"url:"+this.getUrl();
        return ans;
    }
}
