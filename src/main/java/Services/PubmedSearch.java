package Services;

import Bean.QueryRequest;
import Bean.QueryResponse;
import org.springframework.stereotype.Component;

@Component
public interface PubmedSearch {
    public QueryResponse processPubmedSearch(String request);


}
