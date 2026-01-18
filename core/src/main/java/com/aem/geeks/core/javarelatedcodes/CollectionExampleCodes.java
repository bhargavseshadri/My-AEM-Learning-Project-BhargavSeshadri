package com.aem.geeks.core.javarelatedcodes;

import java.util.*;
import java.util.Iterator;

public class CollectionExampleCodes {

    public static void main(String[] args){

        //Example With HashSet
        Set<String> setExample = new HashSet<>();
        setExample.add("Bhargav1");
        setExample.add("bhargav2");
        setExample.add("bhargav3");
        System.out.println(setExample.contains("suchi"));

        // for (String i : setExample){
        //     System.out.println(i);
        // }

        Iterator<String> itr = setExample.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        //Output:
        // false
        //bhargav2
        //bhargav3
        //Bhargav1



        //MAP Example
        Map<Integer, String> hashMapExample = new HashMap<>();
        hashMapExample.put(1, "Apple");
        hashMapExample.put(2, "Banana");
        hashMapExample.put(3, "Cherry");

        // Iterating using enhanced for loop
        // What is this .Entry?
        /*Ans. maps are not directly iterable the way lists or sets are. Java solves this by exposing: map.entrySet()
               which returns Set<Map.Entry<K, V>>.*/

        /*
        entry = one key–value pair
        entry.getKey() → "A"
        entry.getValue() → 10
        */
        for (Map.Entry<Integer, String> entry : hashMapExample.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        // Iterating using iterator
        Iterator<Map.Entry<Integer, String>> iterator = hashMapExample.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}
