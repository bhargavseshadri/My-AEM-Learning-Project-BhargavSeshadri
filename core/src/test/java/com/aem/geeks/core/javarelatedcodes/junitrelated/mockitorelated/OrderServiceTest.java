package com.aem.geeks.core.javarelatedcodes.junitrelated.mockitorelated;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    PaymentGateway paymentGateway;

    @Mock
    NotificationService notificationService;

    @InjectMocks
    OrderService orderService;

    @Captor
    ArgumentCaptor<String> messageCaptor;

    /* --------------------------------------------------
       1. STATIC METHOD MOCKING TEST
       -------------------------------------------------- */
    @Test
    void testStaticDiscountCalculation() {
        try (MockedStatic<DiscountUtil> mockedStatic = mockStatic(DiscountUtil.class)) {

            // mocking static method
            mockedStatic.when(() -> DiscountUtil.getDiscount(anyDouble())).thenReturn(0.2);

            when(paymentGateway.charge(anyDouble())).thenReturn(true);  //WHEN-THEN
            assertTrue(paymentGateway.charge(anyDouble()));                //Assert

            orderService.processOrder(2000, 1);

            // verify static call happened
            mockedStatic.verify(() -> DiscountUtil.getDiscount(2000));
        }
    }

    /* --------------------------------------------------
       2. IF-ELSE BEHAVIOR TEST
       -------------------------------------------------- */
    @Test
    void testIfElse_successBranch() {

        when(paymentGateway.charge(anyDouble())).thenReturn(true);

        boolean result = orderService.processOrder(500, 1);

        assertTrue(result);

        verify(notificationService).sendMsg("Order successful");
    }

    @Test
    void testIfElse_failureBranch() {

        when(paymentGateway.charge(anyDouble())).thenReturn(false);

        boolean result = orderService.processOrder(500, 1);

        assertFalse(result);

        verify(notificationService).sendMsg("Order failed");
    }

    /* --------------------------------------------------
       3. FOR LOOP TEST (quantity iteration impact)
       -------------------------------------------------- */
    @Test
    void testForLoop_execution() {

        when(paymentGateway.charge(anyDouble())).thenReturn(true);
        orderService.processOrder(500, 4); // so here we are giving 4 - based on our logic the for loop will iterate 5 times.

        //calling the charge multiple times because in main for loop the charge method is inside for loop. and we give 5 because the iterations we give above is 4
        // so length - 4 , index - 5
        verify(paymentGateway, times(5)).charge(anyDouble());
    }

    /* --------------------------------------------------
       4. WHILE LOOP TEST (notification retry)
       -------------------------------------------------- */
    @Test
    void testWhileLoop_notificationSentOnce() {

        // So this line sends the flow in to if block
        when(paymentGateway.charge(anyDouble())).thenReturn(true);

        //this line invokes the method
        orderService.processOrder(500, 1);

        // while loop should send notification three times acc to our logic, that's why we have given 3
        verify(notificationService, times(3)).sendMsg(messageCaptor.capture());

        assertEquals("Order successful", messageCaptor.getValue());
    }

    @Test
    void testProcessBulkOrders_arrayListExample() {

        // creating ArrayList input
        List<Double> amounts = new ArrayList<>();
        amounts.add(100.0);
        amounts.add(200.0);
        amounts.add(300.0);

        // stub gateway to always succeed
        when(paymentGateway.charge(anyDouble())).thenReturn(true);

        int result = orderService.processBulkOrders(amounts);

        // verify all items processed
        assertEquals(3, result);

        // verify charge called 3 times (once per list element)
        verify(paymentGateway, times(3)).charge(anyDouble());

        // verify notification message
        verify(notificationService).sendMsg("Processed orders: 3");
    }

}
