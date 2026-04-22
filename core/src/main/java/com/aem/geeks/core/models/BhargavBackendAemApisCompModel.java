package com.aem.geeks.core.models;

import org.apache.sling.api.resource.Resource;

import java.util.List;

public interface BhargavBackendAemApisCompModel {

    String getPersonName();
    String getFatherName();
    String getAverageIncome();
    String[] getMultifieldvalues();
    String getResourcePath();
    String getResourceDetails();
    String isExpectedResourceType();
//    List<Resource> getChildrenList();
    String getValueMapDetails();
}
