package com.aem.geeks.core.services.impl;


import com.aem.geeks.core.services.ProductPriceBranchService;
import org.osgi.service.component.annotations.Component;



//BhargavSeshadri : This is the Branch Service for com/aem/geeks/core/services/impl/ProductServiceImpl.java

@Component(service = ProductPriceBranchService.class)
public class ProductPriceBranchServiceImpl implements ProductPriceBranchService {

    @Override
    public boolean validationProductPrice(String productPrice){
        if(Integer.parseInt(productPrice) > 100){
            return true;
        }else {
            return false;
        }
    }
}
