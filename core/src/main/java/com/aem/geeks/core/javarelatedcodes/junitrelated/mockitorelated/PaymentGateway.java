package com.aem.geeks.core.javarelatedcodes.junitrelated.mockitorelated;

// External dependency (we will mock this)
public class PaymentGateway {

    // real payment call
    public boolean charge(double amount) {
        return true;
    }
}
