package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.config.BhargavOSGiConfig;
import com.aem.geeks.core.services.BhargavOsgiConfigService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;


//BhargavSeshadri - Step:3 - NORMAL OSGI CONFIGURATION (step:2 is this class interface class)
// for STEP:4 - go to com/aem/geeks/core/models/impl/ProductModelImpl.java
@Component(service = BhargavOsgiConfigService.class, immediate = true)  //immediate=true Whenever we want to activate the service immediately after the bundle gets started in that place we use this.
@Designate(ocd = BhargavOSGiConfig.class)  //here we are Designating this service to the osgi configuration
public class BhargavOsgiConfigServiceImpl implements BhargavOsgiConfigService{

    private int phoneNumber;                    //creating the variable for the Fields in our OSGI Configuration
    private String name;
    private String favMovie;
    private String[] countries;
    private String runModes;

    @Activate  //when we want something to be load or executed at the time of this service initiation
    protected void activate(BhargavOSGiConfig bhargavOSGiConfig){  //this is kind of a manditory method(not a complete manditory), in this method we get all the values from config and our class variables
        this.phoneNumber = bhargavOSGiConfig.phoneNumber();
        this.name = bhargavOSGiConfig.name();
        this.favMovie = bhargavOSGiConfig.favMovie();
        this.countries=bhargavOSGiConfig.getCountries();
        runModes=bhargavOSGiConfig.getRunMode();
    }

    @Override
    public int getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFavMovie() {
        return favMovie;
    }

    @Override
    public String[] getCountries() {
        return countries;
    }

    @Override
    public String getRunModes() {
        return runModes;
    }
}
