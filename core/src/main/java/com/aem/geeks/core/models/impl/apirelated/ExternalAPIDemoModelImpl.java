package com.aem.geeks.core.models.impl.apirelated;

import com.aem.geeks.core.models.ExternalAPIDemoModel;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




/*BHARGAVSESHADRI - STEP- 2(Model implimentation) - FETCHING DATA FROM EXTERNAL API
 * For step-1 go to interface file com/aem/geeks/core/models/ExternalAPIDemoModel.java
 * FOR STEP-3 GO TO FILE apps/aemgeeks/components/helloworld/helloworld.html*/

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = ExternalAPIDemoModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ExternalAPIDemoModelImpl implements ExternalAPIDemoModel {

    @SlingObject
    private Resource resource;

    private String id;                          //so these are the fields that are present in our api json "https://jsonplaceholder.typicode.com/users/1"
    private String email;                       //we just create a variables to store the API DATA
    private String name;
    private String userName;
    private String phone;
    private String website;
    private String street;
    private String suite;
    private String city;
    private String lat;
    private String zipcode;
    private String lng;

    public Resource getResource() {
        return resource;
    }

    @PostConstruct
    public void init() {
        String apiUrl = "https://jsonplaceholder.typicode.com/users/1";      //here we are giving the API url to fetch data from that api
        fetchExternalData(apiUrl);                                           //just a defined method by me
    }

    @Override
    public void fetchExternalData(String apiUrl) {     //(1)IMP METHOD WITH ALL THE functionality to fetch the data from the api
        try {
            URL url = new URL(apiUrl);                                              //geting the url
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();      //Here we are opening connection with api,  (HttpURLConnection) this is just a type casting
            conn.setRequestMethod("GET");                                           //To tell which type of method
            conn.setRequestProperty("Accept", "application/json");                   //To tell in which type you want to get the data from API (we can even setHeaders, content type etc)

            if (conn.getResponseCode() != 200) {                                     //Just an exception handling if we receive any error from API
                throw new RuntimeException("failed : http error code :" + conn.getResponseCode());
            }

            Gson gson = new Gson();
            //with the next line : up to now we have all the data in "conn", so now we are converting that data in to STREAM using "conn.getInputStream()"
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());

            // now using json we put that data in to ExternalData.java using gson
            ExternalData data = gson.fromJson(inputStreamReader, ExternalData.class);

            //ExternalData - It is just a helper pojo class to store the data


            //after ExtrenalData.Java gets the data here we fetches that data to store in this class variables and use accordingly
            this.id = data.getId();
            this.name = data.getName();
            this.userName = data.getUserName();
            this.email = data.getEmail();
            this.phone = data.getPhone();
            this.website = data.getWebsite();
            if (data.getAddress() != null) {
                this.street = data.getAddress().getStreet();
                this.city = data.getAddress().getCity();
                this.suite = data.getAddress().getSuite();
                if (data.getAddress().getGeo() != null) {
                    this.lat = data.getAddress().getGeo().getLat();
                    this.lng = data.getAddress().getGeo().getLng();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getSuite() {
        return suite;
    }

    @Override
    public String getCity() {
        return city;
    }

    public String getLat() {
        return lat;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getLng() {
        return lng;
    }
}
