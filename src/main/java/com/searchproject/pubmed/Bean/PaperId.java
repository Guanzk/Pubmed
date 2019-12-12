package com.searchproject.pubmed.Bean;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class PaperId implements Serializable {
    @Id
    private String pmid;
    @Id
    private String aid;
}
