package com.aem.geeks.core.servlets;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;




//bhargavSeshadri : Reference Junit code For ResourceType servlet - GET Method

@ExtendWith(AemContextExtension.class)
class GeeksResourceTypesServletTest {

    AemContext aemContext = new AemContext();
    GeeksResourceTypesServlet geeksResourceTypesServlet = new GeeksResourceTypesServlet();

    @BeforeEach
    void setUp() {
    }

    @Test
    void doGetTest() throws ServletException, IOException {

        //Creating a Resource (multiple other approaches are also present)
        //Below we created a resource and given a title "Geeks Page". now this resource will be in our memory.
        aemContext.build().resource("/content/geeks/test", "jcr:title", "Geeks Page");

        //Now we say the resource we craeted above is our current resource to junit
        aemContext.currentResource("/content/geeks/test");

        //Now we got a requeest and response objects by mocking
        MockSlingHttpServletRequest request = aemContext.request();
        MockSlingHttpServletResponse response = aemContext.response();

        //calling the actual doGet() method from  java class
        geeksResourceTypesServlet.doGet(request, response);

        //Upto now we have setup the required things for doing asserting(actual testing)


        //Assetion
        //response.getOutputAsString() : we use this because in our actual java method it will give the response.
        assertEquals("Page Title = Geeks Page", response.getOutputAsString());

        //We can also test/check others also
        assertEquals(200, response.getStatus());
        assertEquals("GET", request.getMethod());

    }
}