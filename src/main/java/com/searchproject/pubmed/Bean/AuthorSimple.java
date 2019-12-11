package com.searchproject.pubmed.Bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auhtor_list")
public class AuthorSimple {
    @Id
    private String aid;
    private String FullName;
    private String Pmid;
}
