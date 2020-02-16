package com.searchproject.pubmed.Bean.auxiliary;

import lombok.Data;

@Data
public class PmidMongo {
    private long pmid;
    private String title;
    private String pubyear;
}
