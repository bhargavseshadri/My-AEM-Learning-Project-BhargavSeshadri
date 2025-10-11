package com.aem.geeks.core.models;

import java.util.List;
import java.util.Map;

public interface ProductModel {
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
}
