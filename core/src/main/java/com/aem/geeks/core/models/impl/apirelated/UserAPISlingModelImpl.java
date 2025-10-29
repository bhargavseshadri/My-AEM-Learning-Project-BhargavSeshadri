package com.aem.geeks.core.models.impl.apirelated;


import com.aem.geeks.core.models.UserAPISlingModel;
import com.aem.geeks.core.services.UserAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;




/*BhargavSeshadri - STEP: 3 - Creating  a Sling Model So using that we can render the API info on page
 * For Step: 2 Go to -> com/aem/geeks/core/services/impl/UserAPIServiceImpl.java
 * For STEP: 4 go to component and render it on page using htl -> apps/aemgeeks/components/content/bhargavapicomp/bhargavapicomp.html
 * */

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = UserAPISlingModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UserAPISlingModelImpl implements UserAPISlingModel {

    @SlingObject
    private SlingHttpServletRequest request;

    @OSGiService
    private UserAPIService userAPIService;

    private String userJsonString;

    //Data fields in the Json Response from https://dummyjson.com/auth/me
    private String firstName;
    private String lastName;
    private String maidenName;
    private int age;
    private String gender;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String birthDate;
    private String imageUrl;

    private String meJsonString;

    @PostConstruct
    protected void init() {
        try {
            //Here userAPIService.getMeData() will give a mapper object, and from that mapper object we can able to get all the fields using "JsonNode"
            // from the  json we got in response from our API
            //Clearcut MapperObject example is in com/aem/geeks/core/services/impl/UserAPIServiceImpl.java
            JsonNode jsonNode = userAPIService != null ? userAPIService.getMeData() : null;
            if (jsonNode != null) {
                firstName = jsonNode.path("firstName").asText("");
                lastName = jsonNode.path("lastName").asText("");
                maidenName = jsonNode.path("maidenName").asText("");
                age = jsonNode.path("age").asInt(0);
                gender = jsonNode.path("gender").asText("");
                email = jsonNode.path("email").asText("");
                phone = jsonNode.path("phone").asText("");
                username = jsonNode.path("username").asText("");
                password = jsonNode.path("password").asText("");
                birthDate = jsonNode.path("birthDate").asText("");
                imageUrl = jsonNode.path("image").asText("");
            }
        }catch (Exception e) {
            userJsonString = "{\"error\":\"exception\",\"message\":\"" + e.getMessage() + "\"}";
        }
    }

    //Creating Getters of that JSON fields to use in HTL to render on page.
    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getMaidenName() {
        return maidenName;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }




    //Here we are directly printing the json on the page just for my testing : This piece is useful to just see the json response directly on the page in real world cases, before going ahead
    //Go and see the "apps/aemgeeks/components/content/bhargavapicomp/bhargavapicomp.html" htl, there I just printing the json
    @Override
    public String getMeJsonString() throws JsonProcessingException {
        JsonNode jsonNode = userAPIService != null ? userAPIService.getMeData() : null;

        ObjectMapper mapper = new ObjectMapper();
        meJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        return meJsonString;
    }
}
