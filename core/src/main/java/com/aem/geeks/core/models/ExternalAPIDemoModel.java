package com.aem.geeks.core.models;



/*BHARGAVSESHADRI - Step:1 - FETCHING DATA FROM EXTERNAL API - here just creating an interface method
* For STEP:2 -> com/aem/geeks/core/models/impl/apirelated/ExternalAPIDemoModelImpl.java
* */

public interface ExternalAPIDemoModel {

    public void fetchExternalData(String apiUrl);

    public String getId();

    String getEmail();

    String getName();

    String getUserName();

    String getNumber();

    String getWebsite();

    String getStreet();

    String getSuite();

    String getCity();

}
