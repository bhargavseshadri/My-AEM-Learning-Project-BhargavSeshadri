package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.models.BhargavPersonalCompTwoModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;



/*BhargavSeshadri : Step: 3 - Now to fetch the comp A dialog values in comp B using Sling model
*
*
Step: 1 - We should have a component A with some dialog fields. : apps/aemgeeks/components/content/bhargav-personalcomp-one
Step: 2 - Create a component B where we want to use the component A dialog values. : apps/aemgeeks/components/content/bhargav-personalcomp-two
* */
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = BhargavPersonalCompTwoModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BhargavPersonalCompTwoModelImpl implements BhargavPersonalCompTwoModel{

    @SlingObject
    private ResourceResolver resourceResolver;



    @ValueMapValue
    private String selectedTodo;

    //Approach - 1 of getting hold of RESOURCE
    //We have authored the comp-a : apps/aemgeeks/components/content/bhargav-personalcomp-one in this page/resource
    @ResourcePath(path="/content/aemgeeks/us/en/imp-page---bhargav/jcr:content/root/responsivegrid/bhargav_personalcomp")
    private Resource resource;

    @Override
    public String getManufacturerName() {
        return resource.getValueMap().get("manufacturerName", String.class);
    }

    @Override
    public String getProductName() {
        return resource.getValueMap().get("productName", String.class);
    }

    @Override
    public String getProductPrice() {
        return resource.getValueMap().get("productPrice", String.class);
    }




//Approach - 2 of getting hold of RESOURCE
/*
    @SlingObject
    private Resource resource;

    @PostConstruct
    protected void init() {
        //We have authored the comp-a : apps/aemgeeks/components/content/bhargav-personalcomp-one in this page/resource
        resource = resourceResolver.getResource("/content/aemgeeks/us/en/imp-page---bhargav/jcr:content/root/responsivegrid/bhargav_personalcomp");
    }

    @Override
    public String getManufacturerName() {
        return resource.getValueMap().get("manufacturerName", String.class);
    }

    @Override
    public String getProductName() {
        return resource.getValueMap().get("productName", String.class);
    }

    @Override
    public String getProductPrice() {
        return resource.getValueMap().get("productPrice", String.class);
    }*/





    //Rendering the value selected in the dynamic dropdown
    @Override
    public String getSelectedTodo() {
        return selectedTodo;
    }

}
