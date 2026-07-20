package com.aem.geeks.core.servlets;


import com.aem.geeks.core.config.BhargavOSGiConfig;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import javax.servlet.Servlet;
import java.io.IOException;


//BhargavSeshadri - (Only Step) - ResourceType Servlet - GET
//Here we hit the PAGE which have this Resource(aemgeeks/components/structure/content), then this servlet will run and then GET method executed and
// gives us the data we have asked for.

//Hit the below url to GET the data(here page title) you asked for
//http://localhost:4502/content/aemgeeks/us/en/bhargavseshadritestpage/jcr:content.demo.xml
@Component(service = Servlet.class)
@Designate(ocd = BhargavOSGiConfig.class)  //just using the configuration values in the servlet
@SlingServletResourceTypes(
        //So when we hit the page with that "sling: resourceType"  and with the below extensions and selector, then immediately our servlet runs.
        resourceTypes = "aemgeeks/components/structure/content",
        methods = {HttpConstants.METHOD_GET},
        extensions = "xml",
        selectors = "demo"
)
public class BhargavResourceTypeGETServlet extends SlingSafeMethodsServlet {

    private String name;


    @Activate
    protected void activate(BhargavOSGiConfig config){
        this.name = config.name();
    }

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {  //One of the method from SlingSafeMethodsServlet
        final Resource resource = request.getResource();  //here it will get the resource they hit
        response.setContentType("text/plain");             //In which type of format it should give response to us
        response.getWriter().write("Page Title = " + resource.getValueMap().get(JcrConstants.JCR_TITLE) + " :: value from configuration : "+ name);  //getting the title to show to us
    }
}
