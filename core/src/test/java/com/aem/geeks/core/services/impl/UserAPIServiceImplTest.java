package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.config.BhargavConfigForUserAPI;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;






//This code is failing because of some dependency problem so you just study and later fix this uissue
class UserAPIServiceImplTest {

    private UserAPIServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new UserAPIServiceImpl();

        // Mock OSGi config
        BhargavConfigForUserAPI config = mock(BhargavConfigForUserAPI.class);
        when(config.loginUrl()).thenReturn("http://login");
        when(config.protectedUserUrl()).thenReturn("http://me");
        when(config.Username()).thenReturn("user");
        when(config.password()).thenReturn("pass");

        service.activate(config);
    }

    // ===============================
    // loginAndGetToken() SUCCESS
    // ===============================
    @Test
    void testLoginAndGetToken_Success() throws Exception {

        String jsonResponse = "{\"accessToken\":\"abc123\"}";

        CloseableHttpClient client = mock(CloseableHttpClient.class);
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity())
                .thenReturn(new StringEntity(jsonResponse, StandardCharsets.UTF_8));
        when(client.execute(any())).thenReturn(response);

        try (MockedStatic<HttpClients> mocked = mockStatic(HttpClients.class)) {
            mocked.when(HttpClients::createDefault).thenReturn(client);

            String token = service.loginAndGetToken();

            assertEquals("abc123", token);
        }
    }

    // ===============================
    // loginAndGetToken() FAILURE
    // ===============================
    @Test
    void testLoginAndGetToken_FailureStatus() throws Exception {

        CloseableHttpClient client = mock(CloseableHttpClient.class);
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(statusLine.getStatusCode()).thenReturn(401);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity())
                .thenReturn(new StringEntity("Unauthorized", StandardCharsets.UTF_8));
        when(client.execute(any())).thenReturn(response);

        try (MockedStatic<HttpClients> mocked = mockStatic(HttpClients.class)) {
            mocked.when(HttpClients::createDefault).thenReturn(client);

            String token = service.loginAndGetToken();

            assertNull(token);
        }
    }

    // ===============================
    // callMeWithToken() SUCCESS
    // ===============================
    @Test
    void testCallMeWithToken_Success() throws Exception {

        String jsonResponse = "{\"username\":\"emily\"}";

        CloseableHttpClient client = mock(CloseableHttpClient.class);
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity())
                .thenReturn(new StringEntity(jsonResponse, StandardCharsets.UTF_8));
        when(client.execute(any())).thenReturn(response);

        try (MockedStatic<HttpClients> mocked = mockStatic(HttpClients.class)) {
            mocked.when(HttpClients::createDefault).thenReturn(client);

            JsonNode node = service.callMeWithToken("abc123");

            assertNotNull(node);
            assertEquals("emily", node.get("username").asText());
        }
    }

    // ===============================
    // callMeWithToken() FAILURE
    // ===============================
    @Test
    void testCallMeWithToken_FailureStatus() throws Exception {

        CloseableHttpClient client = mock(CloseableHttpClient.class);
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(statusLine.getStatusCode()).thenReturn(403);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity())
                .thenReturn(new StringEntity("Forbidden", StandardCharsets.UTF_8));
        when(client.execute(any())).thenReturn(response);

        try (MockedStatic<HttpClients> mocked = mockStatic(HttpClients.class)) {
            mocked.when(HttpClients::createDefault).thenReturn(client);

            JsonNode node = service.callMeWithToken("abc123");

            assertNull(node);
        }
    }

    // ===============================
    // getMeData() FULL SUCCESS FLOW
    // ===============================
    @Test
    void testGetMeData_Success() throws Exception {

        String loginJson = "{\"accessToken\":\"abc123\"}";
        String meJson = "{\"username\":\"emily\"}";

        CloseableHttpClient client = mock(CloseableHttpClient.class);
        CloseableHttpResponse loginResponse = mock(CloseableHttpResponse.class);
        CloseableHttpResponse meResponse = mock(CloseableHttpResponse.class);

        StatusLine okStatus = mock(StatusLine.class);
        when(okStatus.getStatusCode()).thenReturn(200);

        when(loginResponse.getStatusLine()).thenReturn(okStatus);
        when(loginResponse.getEntity())
                .thenReturn(new StringEntity(loginJson, StandardCharsets.UTF_8));

        when(meResponse.getStatusLine()).thenReturn(okStatus);
        when(meResponse.getEntity())
                .thenReturn(new StringEntity(meJson, StandardCharsets.UTF_8));

        when(client.execute(any()))
                .thenReturn(loginResponse)
                .thenReturn(meResponse);

        try (MockedStatic<HttpClients> mocked = mockStatic(HttpClients.class)) {
            mocked.when(HttpClients::createDefault).thenReturn(client);

            JsonNode node = service.getMeData();

            assertNotNull(node);
            assertEquals("emily", node.get("username").asText());
        }
    }

    // ===============================
    // getMeData() LOGIN FAIL
    // ===============================
    @Test
    void testGetMeData_LoginFails() throws Exception {

        CloseableHttpClient client = mock(CloseableHttpClient.class);
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        when(statusLine.getStatusCode()).thenReturn(401);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity())
                .thenReturn(new StringEntity("Unauthorized", StandardCharsets.UTF_8));
        when(client.execute(any())).thenReturn(response);

        try (MockedStatic<HttpClients> mocked = mockStatic(HttpClients.class)) {
            mocked.when(HttpClients::createDefault).thenReturn(client);

            JsonNode node = service.getMeData();

            assertNull(node);
        }
    }
}