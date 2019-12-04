package test;

import Bean.MedEntity;
import Config.MySQLConfig;
import Server.QueryResult;
import Server.ServerStarter;
import Util.MakeJson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestEntity {
    public static void main(String[] args) throws Exception {
        MySQLConfig.load(ServerStarter.class.getClassLoader().getResource("mysql.conf").getPath());
        String query="neutrophil dysfunction";
        MedEntity entity= QueryResult.processMedEntity(query);
        String entityJson= MakeJson.makeEntityJson(entity);
        log.info("entity json:"+entityJson);
    }
}
