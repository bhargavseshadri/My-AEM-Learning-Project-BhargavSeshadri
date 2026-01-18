package com.aem.geeks.core.javarelatedcodes;

public class PrimeNumOrNot {

    public static void main(String[] args) {
        int num = 11;
        boolean isPrime = true;

        if (num <= 1) {
            isPrime = false;
        } else {
            //here acc to maths we dont need to check all numbers up to the given num. just up to num / 2 is sufficient.
            for (int i = 2; i <= num / 2; i++) {
                System.out.println(i + " bhagi");
                if (num % i == 0) {
                    isPrime = false;
                    break;
                }
            }
        }
        if (isPrime) {
            System.out.println(num + " is a Prime number");
        } else {
            System.out.println(num + " is not a Prime number");
        }
    }
}
