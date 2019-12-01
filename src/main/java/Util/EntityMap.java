package Util;

import java.util.HashMap;

public class EntityMap<K,V> {
    private HashMap<K,V> map;
    private String type;
    public EntityMap(HashMap<K,V> map,String type){
        this.map=map;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<K, V> getMap() {
        return map;
    }

    public void setMap(HashMap<K, V> map) {
        this.map = map;
    }
}
