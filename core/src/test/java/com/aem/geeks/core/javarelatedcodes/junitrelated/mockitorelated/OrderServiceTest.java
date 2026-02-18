package com.aem.geeks.core.javarelatedcodes.junitrelated.mockitorelated;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // enables Mockito annotations
class OrderServiceTest {

    @Mock
    PaymentGateway paymentGateway; // mocking PaymentGateway class - fake dependency

    @Mock
    NotificationService notificationService; //mocking NotificationService class -  fake dependency

    @InjectMocks
    OrderService orderService; // Mockito injects mocks automatically

    @Captor
    ArgumentCaptor<String> messageCaptor; // captures method arguments

    @Spy // partial mock example
    PaymentGateway spyGateway = new PaymentGateway();


    @Test
    void testProcessOrder_success() {

        // ---------- STATIC METHOD MOCKING ----------
        try (MockedStatic<DiscountUtil> mockedStatic = mockStatic(DiscountUtil.class)) {

            mockedStatic.when(() -> DiscountUtil.getDiscount(anyDouble())).thenReturn(0.2);

            // ---------- STUBBING ----------
            when(paymentGateway.charge(anyDouble())).thenReturn(true);

            // ---------- EXECUTION ----------
            boolean result = orderService.processOrder(2000, 2);

            // ---------- ASSERTION ----------
            assertTrue(result);

            // ---------- VERIFICATION ----------
            verify(paymentGateway, times(1)).charge(anyDouble());

            // ---------- ARGUMENT CAPTURING ----------
            verify(notificationService).send(messageCaptor.capture());

            String capturedMessage = messageCaptor.getValue();
            assertEquals("Order successful", capturedMessage);
        }
    }

    @Test
    void testProcessOrder_failure() {

        // stub charge to fail
        when(paymentGateway.charge(anyDouble())).thenReturn(false);

        boolean result = orderService.processOrder(500, 1);

        assertFalse(result);

        // verify failure notification
        verify(notificationService).send("Order failed");
    }

    @Test
    void testSpyExample() {

        // real method runs unless stubbed
        doReturn(false).when(spyGateway).charge(anyDouble());

        boolean result = spyGateway.charge(100);

        assertFalse(result);
    }
}
