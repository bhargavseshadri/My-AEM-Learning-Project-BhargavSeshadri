package com.aem.geeks.core.javarelatedcodes;

public class PalindromeString {
    public static void main(String[] args) {
        String text = "madam";
        boolean isPalindrome = true;

        int start = 0;
        int end = text.length() - 1;

        while (start < end) {
            if (text.charAt(start) != text.charAt(end)) {
                isPalindrome = false;
                break;
            }
            start++;
            end--;
        }

        if (isPalindrome) {
            System.out.println(text + " is a Palindrome");
        } else {
            System.out.println(text + " is not a Palindrome");
        }
    }
}
