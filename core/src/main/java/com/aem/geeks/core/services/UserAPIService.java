package com.aem.geeks.core.services;

import com.fasterxml.jackson.databind.JsonNode;

public interface UserAPIService {

    JsonNode getMeData();

    String loginAndGetToken();

    JsonNode callMeWithToken(String token);
}
