package com.aem.geeks.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/*BhargavSeshadri - Example for PathType Servlet
*STEP:1 : Defining a servlet
*
* Usage: here we are writing a path bound servlet, which is bound to paths ""/bin/pages","/geeks/pages" with get and post methods
* in GET method we will create a json with all the pages where KEY will be the page title amd VALUE will be the page path
* in POST method we will submit the form data to this servlet and later on print that data in logs
*
* Calling the servlet : hit this path --> http://localhost:4502/bin/pages
*
* IMPORTANT:
* STEP:2 (last step for GET method)- If it gives forbidden error while you tried to call the servlet with the provided path like "/geeks/pages". this is because for pathType by default
* few paths were included in the execution paths, and later if we have added any custom paths it give forbidden because it is not there in the execution paths.
* we have to add it there.
* ->ADDING OUR PATH TO THE EXECUTION PATHS:
* 1) go to system/console/configmgr
* 2) search for "Apache Sling Servlet/Script resolver and Error Handler"
* 3) there add your path in the execution paths; eg : /geeks/  - then save it.
* 4) now hit the path you will get the result
*
*
*Step:3 (Only for POST METHOD) : apps/aemgeeks/components/content/formforpathtypeservlet/formforpathtypeservlet.html
* Go to the form component and there add you path (eg: /bin/pages) in the form "action" attribute and for the "method" attribute give "post".

 * */

@Component(service = Servlet.class)
@SlingServletPaths(
        value = {"/bin/pages","/geeks/pages"}     //In pathType servlets we can only add this property, and if we try to add another property it will throw error
)
public class GeeksPathTypeServlet extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(GeeksPathTypeServlet.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        final ResourceResolver resourceResolver = req.getResourceResolver();  //here we are getting the getResourceResolver
        Page page = resourceResolver.adaptTo(PageManager.class).getPage("/content/aemgeeks/us/en"); //here we are adapting it to PAGEMANAGER and then getting the page
        JSONArray pagesArray = new JSONArray();
        try {
            Iterator<Page> childPages = page.listChildren(); //using listChildren() method we are getting the child pages under our page "/content/aemgeeks/us/en"
            while (childPages.hasNext()) {
                Page childPage = childPages.next();  //getting hold of each page
                JSONObject pageObject = new JSONObject();   // and now creating a JSON object
                pageObject.put(childPage.getTitle(), childPage.getPath().toString()); // then i am putting the Page Title(getTitle()) as a key and then Page Path (getPath()) as a value
                pagesArray.put(pageObject); // finally adding the json object in to the JSON Array
            }
        } catch (JSONException e) {
            LOG.info("\n ERROR {} ", e.getMessage());
        }

        resp.setContentType("application/json");
        resp.getWriter().write(pagesArray.toString());  // here are showing the JSON Array we created through response.
    }

    @Override
    protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp)
            throws ServletException, IOException {
        try {
            LOG.info("\n ------------------------STARTED POST-------------------------");
            List<RequestParameter> requestParameterList=req.getRequestParameterList();  // here we are getting all the parameters (means the values we have given in the form) and putting them in a LIST
            for(RequestParameter requestParameter : requestParameterList){  //Looping through that list
                LOG.info("\n ==PARAMETERS===>  {} : {} ",requestParameter.getName(),requestParameter.getString());  //printing that list in the LOG
            }
        }catch (Exception e){
            LOG.info("\n ERROR IN REQUEST {} ",e.getMessage());
        }
        resp.getWriter().write("======FORM SUBMITTED========");  // Sending a response after the form submitted.

    }
}
