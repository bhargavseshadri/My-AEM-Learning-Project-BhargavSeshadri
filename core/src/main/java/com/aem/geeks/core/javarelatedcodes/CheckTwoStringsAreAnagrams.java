package com.aem.geeks.core.javarelatedcodes;

import java.util.Arrays;

public class CheckTwoStringsAreAnagrams {
    public static void main(String[] args) {
        String s1 = "evil";
        String s2 = "vile";

        if (s1.length() != s2.length()) {
            System.out.println("Not Anagrams");
            return;
        }

        char[] a1 = s1.toCharArray();
        char[] a2 = s2.toCharArray();

        Arrays.sort(a1);
        Arrays.sort(a2);

        if (Arrays.equals(a1, a2)) {
            System.out.println("Anagrams");
        } else {
            System.out.println("Not Anagrams");
        }
    }
}
