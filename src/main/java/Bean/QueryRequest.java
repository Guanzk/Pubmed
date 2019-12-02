package Bean;

import java.util.HashMap;

public class QueryRequest {
    private String query;
    private String seq;
    private int pageNumber;
    private String type;
    private String port ;
    private String info;
    private HashMap<String,String >qAndQType;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public HashMap<String, String> getqAndQType() {
        return qAndQType;
    }

    public void setqAndQType(HashMap<String, String> qAndQType) {
        this.qAndQType = qAndQType;
    }
}
