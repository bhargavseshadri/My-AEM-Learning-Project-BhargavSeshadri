package com.aem.geeks.core.models.impl.compositemultifieldrltd;


import com.aem.geeks.core.models.BhargavCompMultifieldSecond;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Approach-2 - printing the values of composite multifield.

// Step:2 - Sling Model to for the Composite Multifield - com/aem/geeks/core/models/impl/compositemultifieldrltd/BhargavCompMultifieldSecond.java
//Step:1 - Create a Composite Multifield - apps/aemgeeks/components/content/bhargav-compmultifield
//Step : 3 - Render the Multifield data in HTL : apps/aemgeeks/components/content/bhargav-compmultifield/bhargav-compmultifield.html
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},adapters = BhargavCompMultifieldSecond.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BhargavCompMultifieldSecondImpl implements BhargavCompMultifieldSecond {

    private static final Logger log = LoggerFactory.getLogger(BhargavCompMultifieldSecondImpl.class);

    @SlingObject
    Resource componentResource;   //here we are injecting our component resource

    @Override
    public List<Map<String, String>> getProductDetailsWithMap() {
        List<Map<String, String>> productDetailsMap = new ArrayList<>();  //here we are creating a list

        try {
            Resource productDetail = componentResource.getChild("compositemultifield");

            if (productDetail != null) {
                for (Resource product : productDetail.getChildren()) {  //here we are getting all the children under productDetail node
                    Map<String, String> productMap = new HashMap<>();
                    productMap.put("question", product.getValueMap().get("question", String.class));  //here we are getting the values of every child node in multifield and putting them in a map
                    productMap.put("answer", product.getValueMap().get("answer", String.class));
                    productDetailsMap.add(productMap);   //adding all these map sets in the main list
                }
            }
        } catch (Exception e) {
            log.info("\n ERROR while getting Book Details {} ", e.getMessage());
        }
        log.info("\n SIZE {} ", productDetailsMap.size());
        return productDetailsMap;
    }
//For Step:3 :


}
