package com.aem.geeks.core.javarelatedcodes;

public class LargestNumInArray {
    public static void main(String[] args) {
        int[] arr = {3, 7, 2, 9};
        int max = arr[0];
        for (int i = 1; i <= arr.length - 1; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        System.out.println(max);
    }
}
