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



@ExtendWith(AemContextExtension.class)  //we have to extend this to get all the aem related objects.
class GeeksResourceTypesServletTest {

    //Creating the required objects
    AemContext aemContext = new AemContext();
    GeeksResourceTypesServlet geeksResourceTypesServlet = new GeeksResourceTypesServlet();

    @BeforeEach
    void setUp() {
    }


    //Junit - For GET Method
    @Test
    void doGetTest() throws ServletException, IOException {

        /*This is a ResourceType servlet, only if we access that resource it will execute, so we have to create a resource*/

        //Creating a Resource (multiple other approaches are also present)
        //Below we created a resource and given a title "Geeks Page". now this resource will be in our memory.
        aemContext.build().resource("/content/geeks/test", "jcr:title", "Geeks Page");

        //Now we say the resource we craeted above is our current resource to junit
        aemContext.currentResource("/content/geeks/test");

        //getting request and response objects by mocking
        MockSlingHttpServletRequest request = aemContext.request();
        MockSlingHttpServletResponse response = aemContext.response();

        //calling the actual doGet() method from  java class
        geeksResourceTypesServlet.doGet(request, response);

        //Upto now we have setup the required things for doing asserting


        //Assertion
        //response.getOutputAsString() : we use this because in our actual java method it will give the response.
        assertEquals("Page Title = Geeks Page", response.getOutputAsString());

        //We can also test/check others also
        assertEquals(200, response.getStatus());
        assertEquals("GET", request.getMethod());

    }


    //Junit - For POST Method
    @Test
    void doPostTest() throws ServletException, IOException {
        //To execute this servlet we need resource, so creating it and making it a current resource
        aemContext.build().resource("/content/geeks/testServlet", "jcr:title", "Geeks Page");
        aemContext.currentResource("/content/geeks/testServlet");

        //Now we got a request and response objects by mocking
        MockSlingHttpServletRequest request = aemContext.request();
        MockSlingHttpServletResponse response = aemContext.response();

        /*Here in this post servlet scenario, Request contains the form data when user submits the form. So currently we only have request object
          we don't have any form data in it, so we have to add a dummy form data to this request.
          * All fields are not required we cand do the testing with just one field as well*/
        request.addRequestParameter("fname", "Seshadri");
        request.addRequestParameter("lname", "Bhargav");    // here this fname, lname are the fieldname in that html form.

        //Calling actual method
        geeksResourceTypesServlet.doPost(request, response);

        //-----SETUP IS DONE

        //Now we have to test the response and form data.
        assertEquals("Seshadri", request.getParameter("fname"));
        assertEquals("Bhargav", request.getParameter("lname"));
        assertEquals("======FORM SUBMITTED========",  response.getOutputAsString());

    }
}