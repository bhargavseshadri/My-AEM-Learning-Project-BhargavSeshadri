package com.aem.geeks.core.models.impl;


import com.aem.geeks.core.models.BhargavBackendAemApisCompModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
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




/***********************************************USAGE OF BACKEND API's**********************/

/*_______________Using Resource

  Resource : Current Resource - Means where am I in the repository */
    @SlingObject
    private Resource resource;

    @Override
    public String getResourcePath() {
        return "Resource Path : " + resource.getPath();
    }

    @Override
    public String getResourceDetails() {
        return "Resource Name : " + resource.getName() + "\n" + "Resource Type : " + resource.getResourceType() + "\n" + "Parent Resource Details : " + resource.getParent()  + "\n" + "Parent Resource Path : " + resource.getParent().getPath();
    }


/*_______________Using VALUEMAP

 ValueMap : It only gives the properties which are present in the current resource.*/

//    In Sling Model we can directly get the ValueMap like this also.
//    @Inject
//    private ValueMap valueMap;



    @Override
    public String getValueMapDetails() {

        //  Getting ValueMap using "Resource"
        ValueMap valueMap = resource.getValueMap();

        //we can print this prop value like this or like above using annotation.
        String personNameUsingValueMap = valueMap.get("personName", String.class);
        String lastModifiedBy = valueMap.get("jcr:lastModifiedBy", String.class);

        return "Person Name printing using ValueMap : " + personNameUsingValueMap + "\n" + "lastModifiedBy : " + lastModifiedBy ;
    }

}


















