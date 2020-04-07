package com.searchproject.pubmed.Bean;

import com.searchproject.pubmed.util.ListConverter;
import lombok.Data;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

@Data
@Table(name = "ai_academic_2019")
@Entity
public class Paper {

    @Id
    private String paperlongid ;

    private String papertitle;

    private String paper_abstract;
    @Convert(converter = ListConverter.class)
    private List<String> paper_authors;

    private Integer paper_publish_year;

    private Integer paper_publish_month;

    private String paper_journal_full_name;

    private String paper_journal_short_name;

    private String paper_conf_full_name;

    private String paper_conf_short_name;

    private String paper_topics;

    private String paper_entities;
    private String paper_citations_in_long_id ;
    private String paper_citations_out_long_id;

    private int paper_citations_num;

    private String paper_url_from ;

    private String paper_url_pdf;

    private String paper_url_doi;

    private String paper_country;

    private String paper_city;

    private String paper_org;

    private String paper_top_level;

    private String paper_doi_num;

    private String paper_lang;

        private Integer paper_influence;
}
