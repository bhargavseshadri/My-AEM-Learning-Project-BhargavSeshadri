package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.config.BhargavConfigForUserAPI;
import com.aem.geeks.core.services.UserAPIService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;




/*BhargavSeshadri - STEP:2 - MAIN LOGIC - Fetching DATA from API which needs ACCESS TOKEN
 * Creating a OSGI Service to use the above config values to login → get token → call protected API -> and getting the response

 * * For STEP:1 go to ->  com/aem/geeks/core/config/BhargavConfigForUserAPI.java
 * For STEP:3 Go to ->  com/aem/geeks/core/models/impl/apirelated/UserAPISlingModelImpl.java
 *
 *
 * MAIN FLOW WE FOLLOWED HERE IS  - VERY IMPORTANT
      1) Prepare HTTP request (HttpPost or HttpGet)
      2) Send it using client.execute() → returns CloseableHttpResponse
      3) Extract response body → EntityUtils.toString(resp.getEntity())
      4) Parse JSON → ObjectMapper.readTree(body) → JsonNode
      5) Access fields → json.path("fieldName")
 */

@Component(service = UserAPIService.class, immediate = true)
@Designate(ocd = BhargavConfigForUserAPI.class)                         //Designate this service to the OSGI Config to use that config values.
public class UserAPIServiceImpl implements UserAPIService {

    private static final Logger log = LoggerFactory.getLogger(UserAPIServiceImpl.class);

    private String loginUrl;
    private String protectedUserUrl;
    private String Username;
    private String password;

    /*- ObjectMapper is a class from Jackson library used to work with JSON in Java. It parses the JSON string you got from an API
        into a usable Java object (JsonNode).
      - ObjectMapper can convert JSON text into Java objects, or into a JsonNode (a tree-like structure to easily read JSON values), and vice versa.
      - Example :
                => JSON we have:
                      {
                        "firstName": "Emily",
                         "age": 28
                       }

                => In our java we give that json string to mapper object  :
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode node = mapper.readTree("{\"firstName\":\"Emily\",\"age\":28}");     //here if we have our json in a variable then we give that in here

                    - Now Node behaves like a tree, so we can get the values of the json.
                        node.get("firstName").asText(); // returns "Emily"
                        node.get("age").asInt();         // returns 28

    */
    private final ObjectMapper mapper = new ObjectMapper();



    @Activate
    protected void activate(BhargavConfigForUserAPI config) {
        this.loginUrl = config.loginUrl();
        this.protectedUserUrl = config.protectedUserUrl();
        this.Username = config.Username();
        this.password = config.password();

        log.info("UserAPIService activated. loginUrl={}, meUrl={}", loginUrl, protectedUserUrl);
    }


    @Override
    public JsonNode getMeData() {
        try {
            // 1. Login and get token from our API
            String token = loginAndGetToken();
            if (token == null) {
                log.info("Login failed, token is null");
                return null;
            }

            // 2. Call /auth/me with token ad get the response and then store it in mapper and return it
            return callMeWithToken(token);

        } catch (Exception e) {
            log.info("Exception in getMeData", e);
            return null;
        }
    }

    @Override
    public String loginAndGetToken() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {  //this line creates an HTTP client to send requests.

            //prepares a POST request to the login URL.
            HttpPost post = new HttpPost(loginUrl);

            //telling the server that we are sending JSON in the post request.
            post.setHeader("Content-Type", "application/json");

            //Just JSON String with username & password.
            String payload = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", Username, password);

            //Here we are sending our JSON String payload to the POST request.
            post.setEntity(new StringEntity(payload, StandardCharsets.UTF_8));

            //client.execute(post) sends the HTTP POST request to the server.
            //In this case, it sends a login request to  login API https://dummyjson.com/auth/login.

            //client.execute(post) this will actually executes, all the lines before just sets the reqd things for this
            //resp variable here holds the response from the server (status code + headers + body).
            //So after this line, the server has received your login credentials, and now returned a response which includes access token also.
            try (CloseableHttpResponse resp = client.execute(post)) {

                // got a HTTP response body above and we are putting that in a String
                String body = EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);

                if (resp.getStatusLine().getStatusCode() >= 200 && resp.getStatusLine().getStatusCode() < 300) { //here we are just checking the status code

                    //Here mapper.readTree(body) converts JSON string "String body" into a tree structure (JsonNode).
                    //So Now "JsonNode json" now allows you to access any field in that json.
                    JsonNode json = mapper.readTree(body);

                    //here we are getting the access token from the json response we got.
                    return json.path("accessToken").asText(null);
                } else {
                    log.info("Login failed: {}", body);
                }
            }
        } catch (Exception e) {
            log.info("Exception during login", e);
        }
        return null;
    }

    @Override
    public JsonNode callMeWithToken(String token) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {  //here this line creates a new HTTP client to send requests.

            //prepares a GET request to the protectedUserUrl endpoint.
            //This line just prepares the request, but it doesn’t send it yet.
            //later we have to execute it with client.execute(get) and pass the token in the headers to fetch the user data.
            HttpGet get = new HttpGet(protectedUserUrl);

            //Here we are adding Authorization header → Bearer <token> means giving the access token we got from the above post request,
            // so the server knows who is requesting.
            get.setHeader("Authorization", "Bearer " + token);

            //telling server that we want JSON response.
            get.setHeader("Accept", "application/json");

            try (CloseableHttpResponse resp = client.execute(get)) {

                //After sending a request, the server returns a response.
                //resp.getEntity() contains the body of the response (the actual data, usually JSON).
                //EntityUtils.toString(...) converts this body from bytes into a Java String.
                //JSON Res we get : {"accessToken":"abc123","username":"emilys"}
                // After converting it in to a string : "{\"accessToken\":\"abc123\",\"username\":\"emilys\"}"
                String body = EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
                if (resp.getStatusLine().getStatusCode() >= 200 && resp.getStatusLine().getStatusCode() < 300) {
                    return mapper.readTree(body);
                } else {
                    log.info("/auth/me call failed: {}", body);
                }
            }
        } catch (Exception e) {
            log.info("Exception during /auth/me call", e);
        }
        return null;
    }

}

