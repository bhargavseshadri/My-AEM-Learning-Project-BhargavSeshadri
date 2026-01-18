package com.aem.geeks.core.javarelatedcodes;

import java.util.Arrays;

public class TwoArraysContainsSameElemntsOrNot {
    public static void main(String[] args) {
        int[] arr1 = {3, 1, 2, 4};
        int[] arr2 = {4, 2, 1, 3};

        // First check length
        if (arr1.length != arr2.length) {
            System.out.println("Arrays do NOT contain the same elements");
            return;
        }

        // Sort both arrays
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        // Compare arrays
        if (Arrays.equals(arr1, arr2)) {
            System.out.println("Arrays contain the same elements");
        } else {
            System.out.println("Arrays do NOT contain the same elements");
        }
    }
}
