package com.aem.geeks.core.servlets;

import com.aem.geeks.core.utils.helpers.GivenNameField;
import com.aem.geeks.core.utils.helpers.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;



/*BhargavSeshadri : Step : 3(last step) Creating a pathtype servlet for getting the data from the API
*
* Step : 2 - apps/aemgeeks/clientlibs/clientlib-bhargav-metadataschema/BhargavMetadataSchemaRLTD.js
*
* * The use of this servlet is, in our Assets metadata we have two fields and if we give ID in one field and the second field will automatically fetch the Name
  belongs to that ID from and API using this Servlet.*/

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "aemgeeks/components/content/bhargav-my-own-resourcetype-servlet-comp",
        selectors = "userlookup",
        extensions = "json",
        methods = HttpConstants.METHOD_POST
)
public class BhargavOwnResourceTypeServlet extends SlingAllMethodsServlet {

    private static final String API_URL = "https://dummyjson.com/users";

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject jsonResponse = new JsonObject();

        String firstName = request.getParameter("firstName");

        if (StringUtils.isBlank(firstName)) {
            jsonResponse.addProperty("error", "First Name is required");
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(API_URL);
            try (CloseableHttpResponse apiResponse = client.execute(httpGet)) {

                String resReader = EntityUtils.toString(apiResponse.getEntity());
                GivenNameField givenNameField = new Gson().fromJson(resReader, GivenNameField.class);
                String email = null;

                if (givenNameField != null && givenNameField.getUsers() != null) {

                    for (User user : givenNameField.getUsers()) {

                        if (user.getFirstName().equalsIgnoreCase(firstName)) {
                            email = user.getEmail();
                            break;
                        }
                    }
                }

                if (email != null) {
                    jsonResponse.addProperty("email", email);
                } else {
                    jsonResponse.addProperty("message", "User not found");
                }
            }

        } catch (Exception e) {

            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("error", e.getMessage());
        }

        response.getWriter().write(jsonResponse.toString());


    }

}
