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
    private String number;
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
            // in simple word we get all the individual data and the we convert in to our class ExternalData.class
            ExternalData data = gson.fromJson(new InputStreamReader(conn.getInputStream()), ExternalData.class);
            /*ExternalData - It is just a helper pojo class to store the data, (and here we are just using the another class here without extending
            or creating any object because gson will automatically creates a object for our ExternalData class
            GSON - Create the object for you (new ExternalData() under the hood)
                 - Read the JSON and Map matching keys to Java fields*/


            this.id = data.getId();             //after ExtrenalData gets the data here we gets that data to store in this class variables and use accordingly
            this.name = data.getName();
            this.userName = data.getUserName();
            this.email = data.getEmail();
            this.number = data.getNumber();
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
    public String getNumber() {
        return number;
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
