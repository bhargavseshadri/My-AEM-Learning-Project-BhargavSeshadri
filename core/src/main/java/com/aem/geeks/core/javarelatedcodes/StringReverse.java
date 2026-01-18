package com.aem.geeks.core.javarelatedcodes;

public class StringReverse {
    public static void main(String[] args) {
        String text = "Hello";  // change this value
        String reversed = "";

        for (int i = text.length() - 1; i >= 0; i--) {
            reversed = reversed + text.charAt(i);
            System.out.println("forming Reversed string: " + reversed);
        }
        System.out.println("Final Reversed string: " + reversed);

    //Output:
    //forming Reversed string: o
    //forming Reversed string: ol
    //forming Reversed string: oll
    //forming Reversed string: olle
    //forming Reversed string: olleH
    //Final Reversed string: olleH


        System.out.println(reverse(text));  // for StringBuilder
    }


    //Using StringBuilder
    public static String reverse(String in) {
        StringBuilder out = new StringBuilder();
        char[] chars = in.toCharArray();

        for (int i = chars.length - 1; i >= 0; i--) {
            out.append(chars[i]);
        }
        return out.toString();
    }
}
