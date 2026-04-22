package com.aem.geeks.core.models.impl;


import com.aem.geeks.core.models.BhargavBackendAemApisCompModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = BhargavBackendAemApisCompModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BhargavBackendAemApisCompModelImpl implements BhargavBackendAemApisCompModel {


    //Printing The Basic Dialog Fields Data
    @ValueMapValue
    private String personName;

    @ValueMapValue
    private String fatherName;

    @ValueMapValue
    private String averageIncome;

    @ValueMapValue
    private String[] multifieldvalues;

    @Override
    public String getPersonName() {
        return personName;
    }

    @Override
    public String getFatherName() {
        return fatherName;
    }

    @Override
    public String getAverageIncome() {
        return averageIncome;
    }

    @Override
    public String[] getMultifieldvalues() {
        return multifieldvalues;
    }
}
