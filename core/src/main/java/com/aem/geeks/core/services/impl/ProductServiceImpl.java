package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.ProductPriceBranchService;
import com.aem.geeks.core.services.ProductService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


//BhargavSeshadri : This is the Service class for the Sling Model -> com/aem/geeks/core/models/impl/ProductModelImpl.java
@Component(service = ProductService.class)
public class ProductServiceImpl implements ProductService{

    @Override
    public boolean validateProductName(String productName){
        if(productName.matches("^[A-Za-z ]+$")){
            return true;
        }else {
            return false;
        }
    }

    @Reference                                                //here we are using the Service class logic
    private ProductPriceBranchService productPriceBranchService;

    public boolean validateProductPrice(String productPrice){
        return productPriceBranchService.validationProductPrice(productPrice);
    }


}
