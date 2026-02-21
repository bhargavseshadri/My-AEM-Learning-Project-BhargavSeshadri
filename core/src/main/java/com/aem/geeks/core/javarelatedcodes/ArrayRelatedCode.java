package com.aem.geeks.core.javarelatedcodes;

public class ArrayRelatedCode {

    public static void main(String[] args) {


/* =========================================================
 2. DIFFERENT WAYS TO DEFINE (DECLARE & INITIALIZE) ARRAYS
 =========================================================*/

        // ---- Method 1: Declare first, then allocate memory ----
        int[] arr1;            // declaration
        arr1 = new int[5];     // allocation (size = 5)

        // ---- Method 2: Declare and allocate together ----
        int[] arr2 = new int[5];

        // ---- Method 3: Declare, allocate, and initialize later ----
        int[] arr3 = new int[3];
        arr3[0] = 10;
        arr3[1] = 20;
        arr3[2] = 30;

        // ---- Method 4: Direct initialization (array literal) ----
        int[] arr4 = {1, 2, 3, 4, 5};

        // ---- Method 5: Using 'new' with initialization ----
        int[] arr5 = new int[]{100, 200, 300};

        // ---- Method 6: Different data types ----
        String[] names = {"Alice", "Bob", "Charlie"};
        double[] prices = {19.99, 29.99, 39.99};
        char[] letters = {'A', 'B', 'C'};
        boolean[] flags = {true, false, true};

        // ---- Method 7: Multidimensional Array (2D Array) ----
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };





/*=========================================================
3. DIFFERENT WAYS TO ACCESS ("CALL") ARRAY ELEMENTS
=========================================================*/

        // Access using index
        System.out.println("arr4[0]: " + arr4[0]);

        // Modify value
        arr4[0] = 99;
        System.out.println("Modified arr4[0]: " + arr4[0]);

        // Access length
        System.out.println("Length of arr4: " + arr4.length);

        // Access 2D array element
        System.out.println("matrix[1][2]: " + matrix[1][2]);





/* =========================================================
 4. DIFFERENT WAYS TO ITERATE THROUGH ARRAYS
=========================================================*/

        // ---- Method 1: Using traditional for loop ----
        System.out.println("\nUsing for loop:");
        for (int i = 0; i < arr4.length; i++) {
            System.out.println(arr4[i]);
        }

        // ---- Method 2: Enhanced for loop (for-each) ----
        System.out.println("\nUsing enhanced for loop:");
        for (int num : arr4) {
            System.out.println(num);
        }

        // ---- Method 3: While loop ----
        System.out.println("\nUsing while loop:");
        int i = 0;
        while (i < arr5.length) {
            System.out.println(arr5[i]);
            i++;
        }

        // ---- Method 4: Using Java Utility (Arrays.toString) ----
        System.out.println("\nUsing Arrays.toString:");
        System.out.println(java.util.Arrays.toString(arr5));

        // ---- Method 5: Looping 2D array ----
        System.out.println("\nLooping 2D array:");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }
}
