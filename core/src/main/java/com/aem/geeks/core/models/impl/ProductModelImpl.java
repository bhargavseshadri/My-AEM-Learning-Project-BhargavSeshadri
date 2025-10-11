package com.aem.geeks.core.models.impl;


import com.aem.geeks.core.models.ProductModel;
import com.aem.geeks.core.services.DemoProductService;
import com.aem.geeks.core.services.ProductService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;




//BhargavSeshadri: Sling Model Demo, here we have all the info about sling model and services
//Note: The Sling Model Should adapt either Resource.class or SlingHttpServletRequest.class. or both
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = ProductModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductModelImpl implements ProductModel{

    @ValueMapValue
    private String productName;

    @ValueMapValue
    private String manufacturerName;

    @ValueMapValue
    private String productPrice;

    @ScriptVariable
    private Page currentPage;

    @OSGiService
    private ProductService productService;

    @Override
    public String getManufacturerName() {
        return manufacturerName;
    }

    @Override
    public String getProductName() {
        if (productService.validateProductName(productName)){  //using the service here
            return productName;
        }else {
            return "Please Provide Valid Name";
        }
    }

    @Override
    public String getProductPrice() {
        if (productService.validateProductPrice(productPrice)){
            return productPrice;
        } else{
            return "Please Provide More Price";
        }
    }

    @Override
    public String getCurrentPageTitle(){
        return currentPage.getTitle();
    }

    @OSGiService(filter = "(component.name=ServiceTwo)")   //demo for using "filter"
    private DemoProductService demoProductService;

    @Override
    public String isReturnName(){
        return demoProductService.returnName();
    }


}
