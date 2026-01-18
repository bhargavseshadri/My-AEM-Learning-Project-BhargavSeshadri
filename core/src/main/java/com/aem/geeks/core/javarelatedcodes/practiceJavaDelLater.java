package com.aem.geeks.core.javarelatedcodes;

import java.util.HashSet;
import java.util.Set;

public class practiceJavaDelLater {
    public static void main(String[] args){

        String a = "bhargav";
        String b = "";

        for (int i = a.length() - 1; i >=0 ; i--) {
            b = a.charAt(i) + b;
        }
        System.out.println("Final Reversed string: " + b);

    }
}
