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
    private String PMID;
    private int AuthorRank;
    private int AffiliationRank;
    private String Affiliation;
    private String Department;
    private String Institution;
    private String Email;
    private String Zipcode;
    private String Location;
    @Id
    private String aid;
    private String city;

}
