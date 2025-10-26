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




/*BhargavSeshadri - STEP:2 - MAIN LOGIC - Creating a OSGI Service to use the above config values to login → get token → call protected API -> and getting the response
 * For STEP:1 go to ->  com/aem/geeks/core/config/BhargavConfigForUserAPI.java
 * For STEP:3 Go to ->  com/aem/geeks/core/models/impl/apirelated/UserAPISlingModelImpl.java
 */

@Component(service = UserAPIService.class, immediate = true)
@Designate(ocd = BhargavConfigForUserAPI.class)
public class UserAPIServiceImpl implements UserAPIService {

    private static final Logger log = LoggerFactory.getLogger(UserAPIServiceImpl.class);

    private String loginUrl;
    private String protectedUserUrl;
    private String Username;
    private String password;
    private int timeout;

    private final ObjectMapper mapper = new ObjectMapper();

    @Activate
    protected void activate(BhargavConfigForUserAPI config) {
        this.loginUrl = config.loginUrl();
        this.protectedUserUrl = config.protectedUserUrl();
        this.Username = config.Username();
        this.password = config.password();
        this.timeout = config.timeout();

        log.info("UserAPIService activated. loginUrl={}, meUrl={}", loginUrl, protectedUserUrl);
    }


    @Override
    public JsonNode getMeData() {
        try {
            // 1️⃣ Login and get token
            String token = loginAndGetToken();
            if (token == null) {
                log.info("Login failed, token is null");
                return null;
            }

            // 2️⃣ Call /auth/me with token
            return callMeWithToken(token);

        } catch (Exception e) {
            log.info("Exception in getMeData", e);
            return null;
        }
    }

    @Override
    public String loginAndGetToken() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(loginUrl);
            post.setHeader("Content-Type", "application/json");
            String payload = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", Username, password);
            post.setEntity(new StringEntity(payload, StandardCharsets.UTF_8));

            try (CloseableHttpResponse resp = client.execute(post)) {
                String body = EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
                if (resp.getStatusLine().getStatusCode() >= 200 &&
                        resp.getStatusLine().getStatusCode() < 300) {

                    JsonNode json = mapper.readTree(body);
                    return json.path("accessToken").asText(null); // Important: DummyJSON uses "accessToken"
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
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(protectedUserUrl);
            get.setHeader("Authorization", "Bearer " + token);
            get.setHeader("Accept", "application/json");

            try (CloseableHttpResponse resp = client.execute(get)) {
                String body = EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
                if (resp.getStatusLine().getStatusCode() >= 200 &&
                        resp.getStatusLine().getStatusCode() < 300) {
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

