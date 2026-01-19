package com.aem.geeks.core.servlets;


import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;


//BhargavSeshadri - (Only Step) - ResourceType Servlet - GET
//Here we hit the PAGE which have this Resource(aemgeeks/components/structure/content), then this servlet will runs and GETs and
// gives us the data we have asked for.

//Hit the below url to GET the data(here page title) you asked for
//http://localhost:4502/content/aemgeeks/us/en/bhargavseshadritestpage/jcr:content.demo.xml
@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "aemgeeks/components/structure/content",  //So when we hit the page with that resourceType page and with the below extensions and selector, then immediately our servlet runs.
        methods = {HttpConstants.METHOD_GET},
        extensions = "xml",
        selectors = "demo"
)
public class BhargavResourceTypeGETServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {  //One of the method from SlingSafeMethodsServlet
        final Resource resource = request.getResource();  //here it will get the resource they hit
        response.setContentType("text/plain");             //In which type of format it should give response to us
        response.getWriter().write("Page Title = " + resource.getValueMap().get(JcrConstants.JCR_TITLE) + " :: Resource Path : "+ resource.getPath());  //getting the title to show to us
    }
}
