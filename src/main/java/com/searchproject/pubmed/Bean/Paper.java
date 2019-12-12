package com.searchproject.pubmed.Bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map.Entry;

@Data
@Table(name = "affiliation_new")
@Entity
@IdClass(PaperId.class)
public class Paper {
    @Id
    private String pmid;
    private int author_rank;
    private int affiliation_rank;
    private String affiliation;
    private String department;
    private String institution;
    private String email;
    private String zipcode;
    private String location;
    @Id
    private String aid;
    private String city;

}
