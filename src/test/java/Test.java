import com.google.gson.Gson;
import com.searchproject.pubmed.dao.RedisDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
    @Autowired
    RedisDao redisDao;
    @org.junit.Test
    public void Test(){
        Set<String>set=redisDao.getAidSet("fibulin-4 shrna");
        Gson gson=new Gson();
        System.out.println(gson.toJson(set));
    }

}
