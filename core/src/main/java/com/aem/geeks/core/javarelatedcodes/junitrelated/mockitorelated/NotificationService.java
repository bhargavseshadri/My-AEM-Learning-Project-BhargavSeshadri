package com.aem.geeks.core.javarelatedcodes.junitrelated.mockitorelated;

// Another dependency (will be mocked)
public class NotificationService {

    public void send(String message) {
        System.out.println("Sending: " + message);
    }
}
