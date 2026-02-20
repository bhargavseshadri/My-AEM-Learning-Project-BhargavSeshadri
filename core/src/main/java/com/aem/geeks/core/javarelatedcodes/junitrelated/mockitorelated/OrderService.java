package com.aem.geeks.core.javarelatedcodes.junitrelated.mockitorelated;

import java.util.List;

public class OrderService {

    PaymentGateway paymentGateway;
    NotificationService notificationService;

    public boolean processOrder(double amount, int quantity) {

        // static method usage
        double discount = DiscountUtil.getDiscount(amount);
        double finalAmount = amount - (amount * discount);

        // for loop example
        for (int i = 0; i < quantity; i++) {
            paymentGateway.charge(finalAmount);  // here we are calling the charge method in our loop.
            System.out.println("Processing item " + i);
        }

        // if-else example
        if (paymentGateway.charge(finalAmount)) {

            // while loop example
            int retry = 0;
            while (retry < 3) {
                notificationService.sendMsg("Order successful");
                retry++;
            }

            return true;
        } else {
            notificationService.sendMsg("Order failed");
            return false;
        }
    }

    // ---------- ARRAYLIST EXAMPLE ----------
    public int processBulkOrders(List<Double> amounts) {

        int successCount = 0;

        // iterating ArrayList using for-each
        for (Double amt : amounts) {

            if (paymentGateway.charge(amt)) {
                successCount++;
            }
        }

        notificationService.sendMsg("Processed orders: " + successCount);

        return successCount;
    }
}

