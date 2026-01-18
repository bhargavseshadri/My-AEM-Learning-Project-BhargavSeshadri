package com.aem.geeks.core.javarelatedcodes;

public class CountVowelsInString {
    public static void main(String[] args) {
        String text = "Hello World";
        int count = 0;

        text = text.toLowerCase();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            }
        }

        System.out.println("Number of vowels: " + count);
    }
}
