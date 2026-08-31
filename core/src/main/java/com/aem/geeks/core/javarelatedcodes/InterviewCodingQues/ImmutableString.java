package com.aem.geeks.core.javarelatedcodes.InterviewCodingQues;

public class ImmutableString {
    public static void main(String[] args) {

        String s1 = "Hello";
        String s2 = s1;

        s1 = s1.concat(" World");

        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);


        //at first both the string variables points to the same literals but after concat s1 points to  a new literral where as s2 still points to s1
    }
}
