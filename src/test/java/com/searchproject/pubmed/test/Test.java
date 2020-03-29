package com.searchproject.pubmed.test;


import com.google.gson.Gson;

import java.util.*;


public class Test {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        double res=0;
        double k=1;
        double button=n;
        double mod=Math.pow(10,9)+7;
        for(int i=1;i<=n;i++){

            k=k*button/i;
            res+=k*i;
            res%=mod;
            button--;
            System.out.println(res+" "+k);
        }

        System.out.print(String.valueOf(res%mod));

    }

}