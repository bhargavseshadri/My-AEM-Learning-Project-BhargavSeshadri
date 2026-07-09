package com.aem.geeks.core.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;



/*BhargavSeshadri : Step : 3(last step) Creating a pathtype servlet for getting the data from the API
*
* Step : 2 - apps/aemgeeks/clientlibs/clientlib-bhargav-metadataschema/BhargavMetadataSchemaRLTD.js
*
* * The use of this servlet is, in our Assets metadata we have two fields and if we give ID in one field and the second field will automatically fetch the Name
  belongs to that ID from and API using this Servlet.*/

@Component(service = Servlet.class)
@SlingServletPaths("/bhargav/userdetails")
public class BhargavMetadataSchemaPathServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(BhargavMetadataSchemaPathServlet.class);


    private static final String API_URL = "https://dummyjson.com/users/";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug(" BhargavMetadataSchemaPathServlet : Execution started Entered in to get block");

        String userId = request.getParameter("id");
        response.setContentType("application/json");

        if (userId == null || userId.isEmpty()) {
            response.getWriter().write("{\"error\":\"User ID is missing\"}");
            return;
        }

        String apiUrl = API_URL + userId;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(apiUrl);

            try (CloseableHttpResponse apiResponse = httpClient.execute(httpGet)) {

                int statusCode = apiResponse.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    response.getWriter().write("{\"error\":\"User not found\"}");
                    return;
                }

                String jsonResponse = EntityUtils.toString(apiResponse.getEntity());
                JsonNode rootNode = objectMapper.readTree(jsonResponse);
                String firstName = rootNode.path("firstName").asText();
                String lastName = rootNode.path("lastName").asText();
                String fullName = firstName + " " + lastName;
                JsonNode responseNode = objectMapper.createObjectNode().put("name", fullName);
                response.getWriter().write(responseNode.toString());
            }catch (Exception e) {
                LOGGER.debug("Exception occured at BhargavMetadataSchemaPathServlet : in catch block-1 {}", e);
            }
        } catch (Exception e) {
            LOGGER.debug("Exception occured at BhargavMetadataSchemaPathServlet : in catch block-2 {}", e);
        }
    }

}
