package test;

import Config.MySQLConfig;
import DAO.ReadAuthorFromMySQL;
import DAO.ReadDataFromRedis;
import Server.ServerStarter;

public class TestSpeed {
    public static void main(String[] args) throws Exception {
        MySQLConfig.load(ServerStarter.class.getClassLoader().getResource("mysql.conf").getPath());

        ReadDataFromRedis r=new ReadDataFromRedis();
        long time=System.currentTimeMillis();
       ReadDataFromRedis.getAidfromAffiliation(r,"Department of Plastic Surgery, Burns Unit, CTO Hospital, Turin, Italy.");
       long end=System.currentTimeMillis();
       System.out.println(end-time);
       time=System.currentTimeMillis();
        ReadAuthorFromMySQL.findAffiliationByAid("1886056");
       end=System.currentTimeMillis();
       System.out.println(end-time);

    }
}
