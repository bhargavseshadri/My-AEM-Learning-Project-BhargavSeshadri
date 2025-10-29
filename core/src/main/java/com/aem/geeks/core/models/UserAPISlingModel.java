package com.aem.geeks.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserAPISlingModel {

    //Creating Getters of that JSON fields to use in HTL to render on page.
    String getFirstName();

    String getLastName();

    String getMaidenName();

    int getAge();

    String getEmail();

    String getGender();

    String getPhone();

    String getUsername();

    String getPassword();

    String getBirthDate();

    String getImageUrl();

    String getMeJsonString() throws JsonProcessingException;
}
