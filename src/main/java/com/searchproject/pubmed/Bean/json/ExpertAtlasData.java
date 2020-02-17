package com.searchproject.pubmed.Bean.json;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class ExpertAtlasData {
    private List<Node> node;
    @Getter(value=AccessLevel.NONE)
    @Setter(value= AccessLevel.NONE)

    private transient List<Node> nodeSet;
    private List<Link>link;
    //避免重复的node
//    public void setNode(List<Node>node){
//        HashMap<String,Integer>map=new HashMap<>();
//        for(Node n:node){
//            map.put(n.getName(),n.getValue());
//        }
//        List<Node>nodes=new ArrayList<>();
//        for(String name:map.keySet()){
//            Node n=new Node();
//            n.setValue(map.get(name));
//            n.setName(name);
//            nodes.add(n);
//        }
//        this.node=nodes;
//    }
    public List<Node> getNode(){
        if(nodeSet==null){
            synchronized (this){
                if(nodeSet==null){
                    HashMap<String,Integer>map=new HashMap<>();
                    for(Node n:node){
                        map.put(n.getName(),n.getValue());
                    }
                    nodeSet=new ArrayList<>();

                    for(String name:map.keySet()){
                        Node n=new Node();
                        n.setValue(map.get(name));
                        n.setName(name);
                        nodeSet.add(n);
                    }
                    node=nodeSet;
                }
            }
        }
        return nodeSet;

    }
}
