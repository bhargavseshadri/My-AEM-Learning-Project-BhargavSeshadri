package com.aem.geeks.core.servlets;


import com.aem.geeks.core.services.BhargavPropertyBtnService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;


/*BhargavSeshadri : For Step: 3 - com/aem/geeks/core/servlets/BhargavPropBtnPathServlet.java
 * Step : 4 :: Node prop adding logic : com/aem/geeks/core/services/impl/BhargavPropertyBtnServiceImpl.java */

//@Component(
//        service = Servlet.class,
//        property = {
//                "sling.servlet.paths=/bhargav/addProperty",
//                "sling.servlet.methods=POST"
//        }
//)

@Component(service = Servlet.class)
@SlingServletPaths("/bhargav/addProperty")
public class BhargavPropBtnPathServlet extends SlingAllMethodsServlet {

    @Reference
    private BhargavPropertyBtnService bhargavPropertyBtnService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        String resourcePath = request.getParameter("resourcePath");
        Resource resource = request.getResourceResolver().getResource(resourcePath);
        boolean success = false;

        try {
            if (resource != null) {
                success = bhargavPropertyBtnService.addProperty(resource);
            }
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");

        if (success) {
            response.getWriter().write("{\"status\":\"success\"}");
        } else {
            response.getWriter().write("{\"status\":\"error or invalid reource\"}");
        }
    }
}
