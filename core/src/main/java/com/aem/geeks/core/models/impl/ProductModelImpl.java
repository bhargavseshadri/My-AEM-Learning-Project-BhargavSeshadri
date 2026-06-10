package com.aem.geeks.core.models.impl;


import com.aem.geeks.core.models.ProductModel;
import com.aem.geeks.core.services.BhargavOsgiConfigService;
import com.aem.geeks.core.services.DemoProductService;
import com.aem.geeks.core.services.OSGiFactoryConfig;
import com.aem.geeks.core.services.ProductService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//BhargavSeshadri - STEP:3 - Sling Model Demo, here we have all the info about sling model and services
//Note: The Sling Model Should adapt either Resource.class or SlingHttpServletRequest.class. or both
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = ProductModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductModelImpl implements ProductModel{

    private static final Logger LOG = LoggerFactory.getLogger(ProductModelImpl.class);


    @ValueMapValue               //using this we are getting the values given to the dialog fields
    private String productName;

    @ValueMapValue
    private String manufacturerName;

    @ValueMapValue
    private String productPrice;

    @ValueMapValue
    private String productImageReference;

    @ScriptVariable
    private Page currentPage;

    @OSGiService
    private ProductService productService;     //BhargavSeshadri - Step:4 - Sling Model - Using of Service in our model

    @Override
    public String getManufacturerName() {
        return manufacturerName;
    }

    @Override
    public String getProductImageReference() {
        return productImageReference;
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

    @OSGiService(filter = "(component.name=ServiceTwo)")   //BhargavSeshadri - demo for using "filter"
    private DemoProductService demoProductService;

    @Override
    public String isReturnName(){
        return demoProductService.returnName();
    }


    //Normal Multifield
    @ValueMapValue
    private String[] multifieldvalues;

    @Override
    public String[] getMultifieldvalues(){
        return multifieldvalues;
    }







/*********************************************************NORMAL CONFIGURATION RELATED CODE***************************************************************/
//BhargavSeshadri - Normal OSGI Configuration - STEP:4 - Using/calling the service in SLING MODEL
// FOR STEP:3 got to com/aem/geeks/core/services/impl/BhargavOsgiConfigServiceImpl.java
// for STEP:5 (last step) - go to apps/aemgeeks/components/content/bhargav-personalcomp-one/bhargav-personalcomp-one.html

    @OSGiService
    BhargavOsgiConfigService bhargavOsgiConfigService;  //getting the service linked to our configuration

    @Override
    public int getNumber(){
        return bhargavOsgiConfigService.getPhoneNumber();
    }

    @Override
    public String getName(){
        return bhargavOsgiConfigService.getName();
    }

    @Override
    public String getFavMovie(){
        return bhargavOsgiConfigService.getFavMovie();
    }

    @Override
    public String[] getCountries(){
        return bhargavOsgiConfigService.getCountries();
    }

    @Override
    public String getRunModes(){
        return bhargavOsgiConfigService.getRunModes();
    }








/*********************************************************FACTORY CONFIGURATION RELATED CODE***************************************************************/
/*BhargavSeshadri - Step:3 - using the osgi factory configuration service here
* For Step:2 - com/aem/geeks/core/services/impl/OSGiFactoryConfigImpl.java
* For Step:4 (Last Step) - apps/aemgeeks/components/content/bhargav-personalcomp-one/bhargav-personalcomp-one.html
* */

    @OSGiService
    OSGiFactoryConfig oSGiFactoryConfig;   //creating our service object

    @Override
    public List<OSGiFactoryConfig> getAllOSGiConfigs() {  //here we are returning the list
        return oSGiFactoryConfig.getAllConfigs();
    }


}
