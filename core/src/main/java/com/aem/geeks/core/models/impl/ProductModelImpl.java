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

    @ScriptVariable
    private Page currentPage;

    @OSGiService
    private ProductService productService;     //BhargavSeshadri - Step:4 - Sling Model - Using of Service in our model

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

    @OSGiService(filter = "(component.name=ServiceTwo)")   //BhargavSeshadri - demo for using "filter"
    private DemoProductService demoProductService;

    @Override
    public String isReturnName(){
        return demoProductService.returnName();
    }

/*********************************************************MULTIFIELD RELATED CODE***************************************************************/

//BhargavSeshadri - From here Composite multifield related code (Step:2 )(Step:1 apps/aemgeeks/components/content/slingmodelproductcomp/_cq_dialog/.content.xml)
//here this multifield will take a every set of values and store them in a new node. and using the below java logic we will render those values on page
    @Inject
    Resource componentResource;   //here we are injecting our component resource

    @Override
    public List<Map<String, String>> getProductDetailsWithMap() {
        List<Map<String, String>> productDetailsMap=new ArrayList<>();  //here we are creating a list
        try {
            Resource productDetail=componentResource.getChild("productdetails");  //here we got the current resource using componentResource and from there we are going to the child multifield node
            if(productDetail!=null){
                for (Resource product : productDetail.getChildren()) {  //here we are getting all the children under productDetail node
                    Map<String,String> productMap=new HashMap<>();      //here we are creating a map to put the values of every item node means for every iteration of multifield
                    productMap.put("productname",product.getValueMap().get("productnamemultifield",String.class));  //here we are getting the values of every child node in multifield and putting them in a map
                    productMap.put("buyername",product.getValueMap().get("buyerName",String.class));
                    productMap.put("quantity",product.getValueMap().get("quantity",String.class));
                    productDetailsMap.add(productMap);   //adding all these map sets in the main list
                }
            }
        }catch (Exception e){
            LOG.info("\n ERROR while getting Book Details {} ",e.getMessage());
        }
        LOG.info("\n SIZE {} ",productDetailsMap.size());
        return productDetailsMap;
    }
//For Step:3 : render these values using htl -> apps/aemgeeks/components/content/slingmodelproductcomp/slingmodelproductcomp.html


/*********************************************************NORMAL CONFIGURATION RELATED CODE***************************************************************/
//BhargavSeshadri - Normal OSGI Configuration - STEP:4 - Using/calling the service in SLING MODEL
// FOR STEP:3 got to com/aem/geeks/core/services/impl/BhargavOsgiConfigServiceImpl.java
// for STEP:5 (last step) - go to apps/aemgeeks/components/content/slingmodelproductcomp/slingmodelproductcomp.html

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
* For Step:4 (Last Step) - apps/aemgeeks/components/content/slingmodelproductcomp/slingmodelproductcomp.html
* */

    @OSGiService
    OSGiFactoryConfig oSGiFactoryConfig;   //creating our service object

    @Override
    public List<OSGiFactoryConfig> getAllOSGiConfigs() {  //here we are returning the list
        return oSGiFactoryConfig.getAllConfigs();
    }


}
