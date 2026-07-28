package com.aem.geeks.core.javarelatedcodes;

import java.util.*;

public class CollectionCodingExamples {

    public static void main(String[] args) {
        //1 - How many times a element is repeating
        int[] arr = {1,2,2,3,1,4};

        Map<Integer, Integer> map = new HashMap<>();
        for(int num : arr){
            map.put(num, map.getOrDefault(num, 0) + 1);
//            System.out.println(map.getOrDefault(num, 0));
        }
//        System.out.println(map);






        //2- find duplicate elements

    }



}
