package com.aem.geeks.core.javarelatedcodes;

import java.util.Arrays;

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


        //or simply we can also write like this
        Arrays.sort(arr);
        System.out.println(arr[arr.length-1]);
    }
}
