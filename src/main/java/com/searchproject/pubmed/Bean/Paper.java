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
    private String paper_long_id ;

    private String paper_title;

    private String paper_abstract;
    @Convert(converter = ListConverter.class)
    private List<String> paper_authors;

    private int paper_publish_year;

    private int paper_publish_month;

    private String paper_journal_full_ame;

    private String paper_journal_short_name;

    private String paper_conf_full_name;

    private String paper_conf_short_name;
    @Convert(converter = ListConverter.class)
    private List<String> paper_topics;
    @Convert(converter = ListConverter.class)
    private List<String> paper_entities;
    @Convert(converter = ListConverter.class)
    private List<String> paper_citations_in_long_id ;
    @Convert(converter = ListConverter.class)
    private List<String> paper_citations_out_long_id;

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

    private int paper_influence;
}
