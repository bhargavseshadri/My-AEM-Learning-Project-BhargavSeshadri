package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.DemoProductService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;



//BhargavSeshadri : This is SERVICE - 2 ; the class also implimented DemoProductService interface
@Component(service = DemoProductService.class,name = "ServiceTwo") //here this name is used in filter while calling in model
@ServiceRanking(1004)  //so this service have the highest rank rt, then this will gets executed
public class DemoProductModelServiceTwoImpl implements DemoProductService{

    @Override
    public String returnName(){
        return "Demo ProductModel Service TWO Loaded";
    }
}
