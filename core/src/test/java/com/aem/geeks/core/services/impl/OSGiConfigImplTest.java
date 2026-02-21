package com.aem.geeks.core.services.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

//bhargavSeshadri : Reference for writing Junits for OSGI Configurations


@ExtendWith(AemContextExtension.class)
class OSGiConfigImplTest {

    AemContext aemContext = new AemContext();
    OSGiConfigImpl configTest;

    //This is the annotation class I have created for my osgi config
    OSGiConfigImpl.ServiceConfig serviceConfig;

    //variables we are assigning in the avtivate method.
    private String serviceName;


    @BeforeEach
    void setUp() {

        //This is a another way of creating our class obj
        configTest = aemContext.registerService(new OSGiConfigImpl());

        //Now we also want to get the our osgi config annotation also(in this case its name is ServiceConfig)
        //we just create a mock of that main annotation config
        serviceConfig = Mockito.mock(OSGiConfigImpl.ServiceConfig.class);

        stubbingMethod();

        //Just making our activate method to run before every test method
        activateTest();

    }

    @Test
    void activateTest() {
        //Now for "Activate" method
        //our activate method in osgi configs usually takes "annotation config" we created. so here also we are passing that mock.
        configTest.activate(serviceConfig);
        System.out.println("Hey bhargav  here");
    }


    //In here we are doing the stubbing
    void stubbingMethod() {

        //Using the serviceConfig mock we have created, we are setting the value to give to us, when we call the method. so now we can write assertions
        //After we done this then automatically this will set "serviceName = "Seshadri Bhargav";" in our activate method in our actual java class.
        //So after that if we call "getServiceName()" then it will return --> "Seshadri Bhargav"
        when(serviceConfig.serviceName()).thenReturn("Seshadri Bhargav");

        //Stubbing for others as well
        when(serviceConfig.serviceCount()).thenReturn(3);
        when(serviceConfig.liveData()).thenReturn(true);
        when(serviceConfig.runMode()).thenReturn("Author");

        //Stubbing for an ARRAY
        when(serviceConfig.countries()).thenReturn(new String[]{"in", "de"});


    }

    @Test
    void getServiceName() {

        //We cannot directly write this assertion - if we directly write "configTest.getServiceName()" then this gives Null.
        //Before writing this we have to do stubbing and intentionally say when I call this method then return this value.
        //We do that stubbing in stubbingMethod() - go there and see.
        assertEquals("Seshadri Bhargav", configTest.getServiceName());

    }

    @Test
    void getServiceCount() {
        assertEquals(3, serviceConfig.serviceCount());
    }

    @Test
    void isLiveData() {
        assertTrue(serviceConfig.liveData());
    }

    @Test
    void getCountries() {
        //For an Array
        assertAll(
                () -> assertEquals(2,configTest.getCountries().length),
                () -> assertEquals("in",configTest.getCountries()[0])
        );
    }

    @Test
    void getRunModes() {
        assertEquals("Author", serviceConfig.runMode());
    }
}