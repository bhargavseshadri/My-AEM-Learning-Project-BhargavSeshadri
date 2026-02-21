package com.aem.geeks.core.javarelatedcodes.junitrelated.mockitorelated;

//Java class with a Static method (static mocking example)
public class DiscountUtil {

    public static double getDiscount(double amount) {
        if (amount > 1000) {
            return 0.2;
        }
        return 0.1;
    }
}