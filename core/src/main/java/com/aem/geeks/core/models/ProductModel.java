package com.aem.geeks.core.models;

import com.aem.geeks.core.services.OSGiFactoryConfig;

import java.util.List;
import java.util.Map;

//BhargavSeshadr - STEP:2 - SLING MODEL

public interface ProductModel {
    public String getProductImageReference();

    public String getProductName();
    public String getProductPrice();
    public String getManufacturerName();
    public String getCurrentPageTitle();
    public String isReturnName();

    List<Map<String, String>> getProductDetailsWithMap();  //this is multifield method

    int getNumber();  //these are osgi config related methods
    String getName();
    String getFavMovie();
    String[] getCountries();
    String getRunModes();

    List<OSGiFactoryConfig> getAllOSGiConfigs();
}
