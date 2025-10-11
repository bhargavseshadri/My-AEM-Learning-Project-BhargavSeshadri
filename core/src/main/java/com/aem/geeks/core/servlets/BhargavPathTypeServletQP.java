package com.aem.geeks.core.servlets;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import java.io.IOException;




/*BhargavSeshadri: QUERY PARAMETERS FETCHING USING PATH TYPE SERVLET DEMO
*
*  Servlet usage: this servlet will get executed when we hit the path /bin/queryparamdemo and then in response it will give the info present in the query params.
*  Step: (only step) : com/aem/geeks/core/servlets/BhargavPathTypeServletQP.java

To execute the servlet : http://localhost:4502/bin/queryparamdemo?name=Bhargav&age=25
* output looks like : Name: Bhargav; Age: 25
* */

@Component(service = {Servlet.class})
@SlingServletPaths(
        value = {"/bin/queryparamdemo"}  // You can call this URL directly
)
public class BhargavPathTypeServletQP extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {

        // Get query parameters from URL
        String name = request.getParameter("name");
        String age = request.getParameter("age");

        // Set response type to plain text
        response.setContentType("text/plain");

        // Write query parameters in plain text
        response.getWriter().write("Name: " + name + "\n");
        response.getWriter().write("Age: " + age);
    }
}

