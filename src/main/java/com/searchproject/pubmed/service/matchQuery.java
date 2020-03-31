package com.searchproject.pubmed.service;

import com.searchproject.pubmed.dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class matchQuery {
@Autowired
    RedisDao redisDao;


    Set<String> aidSet = new HashSet<String>();
    Set<String> entitySet = new HashSet<String>();

    public Set<String> getAid() {
        return aidSet;
    }

    public Set<String> getEntity() {
        return entitySet;
    }



    public String getArrString(String[] array) {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            if (str.equals("")) {
                str = array[i];
            } else {
                str = str + " " + array[i];
            }
        }
        return str;
    }

    // redis查询，并返回类型
    public String getQType(String str) {
        String result = "";
        Set<String> aid = redisDao.getAidSet(str);
        Set<String> entityPMID = redisDao.getPmidSet(str);
        // 去除·和.再查找expert的名字
        String str1 = str.replace('.', ' ');
        str1 = str1.replace('·', ' ');
        Set<String> aid1 = redisDao.getAidSet(str1);
        if (aid.size() > 0) {
            result = "Expert";
            aidSet.addAll(aid);
        } else if (aid1.size() > 0) {
            result = "Expert";
            aidSet.addAll(aid1);
        } else if (entityPMID.size() > 0) {
            result = "Entity";
            entitySet.add(str);
        }
        return result;
    }

    public String[] delete(int index, String array[]) {
        String[] arrNew = new String[array.length - 1];
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        System.arraycopy(array, 0, arrNew, 0, arrNew.length);
        return arrNew;
    }

    public List<queryClass> ngramMatch(String str) {
        List<queryClass> rst = new ArrayList<queryClass>();
        if (str.equals("") == false) {
            String[] input = str.split(" ");
            // 找到左边最大
            queryClass qc = leftMax(input);
            String subseq = qc.get_maxQuery();
            if (subseq.equals("") == false) { // 有结果
                int len = subseq.split(" ").length;
                for (int i = 0; i < len; i++) { // 循环次数跟subseq长度相同
                    input = delete(0, input); // 删除最左边的一个元素
                }
                rst = ngramMatch(getArrString(input));

                rst.add(qc);
                return rst;
            } else { // 没有结果
                input = delete(0, input); // 删除最左边的一个元素
                rst = ngramMatch(getArrString(input));
                return rst;
            }
        }
        return rst;
    }

    // 一直找最左边的来匹配，删除最后一个元素，不断循环，直到最左边的一个元素也匹配不出结果
    public queryClass leftMax(String[] input) {
        queryClass qc = new queryClass();
        qc.set_maxQuery("");
        qc.set_queryType("");
        while (input.length > 0) {
            String subSeq = "";
            if (input.length <= 20) {
                subSeq = getArrString(input);
            } else { // 超过20的，按照20来算
                for (int i = 0; i < 21; i++) {
                    if (subSeq.equals("")) {
                        subSeq = input[i];
                    } else {
                        subSeq = subSeq + " " + input[i];
                    }
                }
            }

            String qt = getQType(subSeq);
            if (qt.equals("") == false) { // 如果匹配得到Type
                qc.set_maxQuery(subSeq);
                qc.set_queryType(qt);
                return qc;
            } else if (subSeq.split(" ").length > 1) {
                // 删除最右边的一个元素
                input = delete(input.length - 1, input);
            } else if (subSeq.split(" ").length == 1) { // 长度为1，且匹配不到，不设置qc，但仍返回
                return qc;
            }

        }
        return qc;
    }

    public static void main(String[] args) throws IOException {
        matchQuery mq = new matchQuery();
        String query = "rna P O Schneider";
        query = query.toLowerCase();
        // String query = "I like to learn deep learning and want long short term memory
        // and tsinghua university";
        // String[] qarr = query.split(" ");
        // queryClass qc = mq.leftMax(qarr);
        // System.out.println("qc is: " + qc.get_maxQuery() + " " + qc.get_queryType());

        List<queryClass> lqc = mq.ngramMatch(query);
        Set<String> aidSet = mq.getAid();
        Set<String> entitySet = mq.getEntity();
        System.out.println("Aid:");
        for (String a : aidSet) {
            System.out.println(a);
        }
        System.out.println("__________________" + "\n");
        System.out.println("Entity:");
        for (String e : entitySet) {
            System.out.println(e);
        }
        System.out.println("__________________" + "\n");
        for (int i = 0; i < lqc.size(); i++) {
            System.out.println(lqc.get(i).get_maxQuery() + "\t" + lqc.get(i).get_queryType());
        }
    }

}
