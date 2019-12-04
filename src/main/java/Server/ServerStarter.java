package Server;

import Config.MySQLConfig;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerStarter {
    public static void main(String[] args) {
        try {
            MySQLConfig.load(ServerStarter.class.getClassLoader().getResource("mysql.conf").getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new ClassPathXmlApplicationContext("classpath:rpc-invoke-config-server.xml");
    }
}
