//package com.searchproject.pubmed.dao;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//
//@Slf4j
//public class AiId {
//    public static ArrayList<String> getIDList(String query, String queryType, String ansType){
//        ArrayList<String> keys = getFormatKeyList(query, RedisConfig.getInt("RETRIEVE_"+queryType+"_"+ansType),RedisConfig.getInt("FORMAT_"+queryType+"_NAME"));
//        log.info("RETRIEVE_"+queryType+"_"+ansType+"===="+"FORMAT_"+queryType+"_NAME");
//        log.info("KeysSet:"+keys);
//        ArrayList<String> iDList = getIDListByFormatKey(keys,RedisConfig.getInt("RETRIEVE_"+queryType+"_"+ansType));
//        log.info("RETRIEVE_"+queryType+"_"+ansType+"||RedisNum:"+RedisConfig.getInt("RETRIEVE_"+queryType+"_"+ansType));
//        log.info("IDList:"+iDList);
//        return iDList;
//    }
//}
