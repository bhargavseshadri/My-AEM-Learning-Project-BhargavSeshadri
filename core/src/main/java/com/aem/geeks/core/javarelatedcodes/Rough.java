package com.aem.geeks.core.javarelatedcodes;

import java.util.*;

public class Rough {
    public static void main(String[] args) {
        TreeSet<Integer> bhaList = new TreeSet<>();

        bhaList.add(6);
        bhaList.add(5);
        bhaList.add(3);
        bhaList.add(4);
        bhaList.add(2);
        bhaList.add(1);


        Iterator<Integer> it = bhaList.iterator();
        while (it.hasNext()){
            Integer temp = it.next();
            System.out.println("temp : "+temp);
        }




//        System.out.println("List : "+bhaList);
//        System.out.println("Set : "+setting);
    }
}
