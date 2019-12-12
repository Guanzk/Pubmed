package com.searchproject.pubmed.Bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "affiliation_pubyear_count")
public class AffiliationCount {
    @Id
    private String affiliation;
    private int total_count;
}
