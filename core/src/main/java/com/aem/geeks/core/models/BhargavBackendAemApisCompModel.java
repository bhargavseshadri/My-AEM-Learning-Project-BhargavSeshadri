package com.aem.geeks.core.models;

import org.apache.sling.api.resource.Resource;

import javax.annotation.PostConstruct;
import java.util.List;

public interface BhargavBackendAemApisCompModel {

    @PostConstruct
    String postConstructMethod();

    String getPersonName();
    String getFatherName();
    String getAverageIncome();
    String[] getMultifieldvalues();
    String getResourcePath();
    String getResourceDetails();
    String isExpectedResourceType();
    List<Resource> getChildrenList();
    String getValueMapDetails();

    String getCurrentPageDetails();

    String getPropsUsingValueMap();

    List<String> childPageDetails();
}
