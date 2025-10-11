package com.aem.geeks.core.services;


//BhargavSeshadri - STEP:2 - NORMAL OSGI CONFIGURATION (for step:1 com/aem/geeks/core/config/BhargavOSGiConfig.java) create an osgi configuration and come here
public interface BhargavOsgiConfigService {
    int getPhoneNumber();

    String getName();

    String getFavMovie();

    String[] getCountries();

    String getRunModes();
}
