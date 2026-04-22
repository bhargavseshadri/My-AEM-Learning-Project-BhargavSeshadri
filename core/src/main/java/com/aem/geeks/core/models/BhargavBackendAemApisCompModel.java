package com.aem.geeks.core.models;

import org.apache.sling.api.resource.Resource;

public interface BhargavBackendAemApisCompModel {

    String getPersonName();
    String getFatherName();
    String getAverageIncome();
    String[] getMultifieldvalues();
    String getResourcePath();
    String getResourceDetails();


    String getValueMapDetails();
}
