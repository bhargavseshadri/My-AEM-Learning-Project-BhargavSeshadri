package com.aem.geeks.core.javarelatedcodes.junitrelated.mockitorelated;

public class OrderService {

    PaymentGateway paymentGateway;
    NotificationService notificationService;

    public boolean processOrder(double amount, int quantity) {

        // static method usage
        double discount = DiscountUtil.getDiscount(amount);
        double finalAmount = amount - (amount * discount);

        // for loop example
        for (int i = 0; i < quantity; i++) {
            System.out.println("Processing item " + i);
        }

        // if-else example
        if (paymentGateway.charge(finalAmount)) {

            // while loop example
            int retry = 0;
            while (retry < 1) {
                notificationService.send("Order successful");
                retry++;
            }

            return true;
        } else {
            notificationService.send("Order failed");
            return false;
        }
    }
}

