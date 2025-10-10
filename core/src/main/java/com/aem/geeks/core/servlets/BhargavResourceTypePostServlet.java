package com.aem.geeks.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.jcr.*;
import javax.servlet.Servlet;
import java.io.IOException;



/*BhargavSeshadri : Step:2 for RESOURCETYPE - POST SERVLET
 * For step:2 : apps/aemgeeks/components/content/registrationformservlet/registrationformservlet.html
 *
 * Here this servlet will gets executed when ever the user gives the form values and hits the submit
 * And then in CRX under it will create a new newUser node with user details, for every new user It creates a new node under "/content/aemgeeks/us/en/registration-form-post-servlet/jcr:content"
 * The form details will only stored under this node because I have given this path in form "action" attribute
 *
 * IMPORTANT: After the servlet, then go to the form and add method="post" action="Path where you want to store thev data"; add these in the form tag*/

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        methods = {HttpConstants.METHOD_POST},  //here only using post method, if want we can use GET also
        resourceTypes = "aemgeeks/components/structure/page",
        extensions = "txt",
        selectors = "bha"
)
public class BhargavResourceTypePostServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    public static int count = 0;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse res) throws IOException {  //Get Method
        final Resource resource = req.getResource();
        res.setContentType("text/plain");
        res.getWriter().write("Form Submitted Successfully");
    }

    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse res) throws IOException {
        Session session = req.getResourceResolver().adaptTo(Session.class);   //Here I am getting the Session using resourceResolver

        try {                                                    //Going down we are getting the node, modifying, adding the node and setting the properties using session
            if (session != null) {
                Node postToResourceNode = session.getNode("/content/aemgeeks/us/en/registration-form-post-servlet/jcr:content");  // this is the path of my page where i have used this form component
                Node newUserNode = postToResourceNode.addNode("newUser" + ++count, "nt:unstructured");
                newUserNode.setProperty("name", req.getParameter("name"));       //here the name, password, email etc these should match the values given in the form "name" attribute
                newUserNode.setProperty("phonenumber", req.getParameter("phone"));
                newUserNode.setProperty("email", req.getParameter("email"));
                newUserNode.setProperty("password", req.getParameter("password"));

                Property emailProperty = newUserNode.getProperty("email");
                Property phoneProperty = newUserNode.getProperty("phonenumber");

                String strValue = emailProperty.getString();
                String userName = strValue.substring(0, 5);
                newUserNode.setProperty("username", userName);

                res.getWriter().write("Congrats!! Your Sign Up was successful  -- Your User Name is " + userName);  //sending a response after POST
                session.save();

                res.setContentType("text/plain");
                res.setStatus(200);
                res.getWriter().write("User node created successfully! Username: " + userName);
                res.getWriter().flush();

                // Important: Stop SlingPostServlet from processing further
                req.setAttribute("sling.post.servlet", Boolean.TRUE);
            } else {
                res.sendError(500, "Could not obtain JCR session.");
            }

        } catch (PathNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
