package com.searchproject.pubmed.Bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "new_patent_list")
@Entity
public class Patent_new {
    @Id
    private String patentid;
    private String patent_inventors, patenttitle, patent_abstract, patent_date, patent_number, patent_url, patent_classify, patent_assignees;
    @Override
    public String toString(){
        String ans = "";
        ans+="id:"+this.getPatentid()+"title:"+this.getPatenttitle()+"abstract:"+this.getPatent_abstract()+"inventors:"+this.getPatent_inventors()+"classify:"+this.getPatent_classify()+"date:"+this.getPatent_date()+"number:"+this.getPatent_number()+"assignees:"+this.getPatent_assignees()+"url:"+this.getPatent_url();
        return ans;
    }
}
