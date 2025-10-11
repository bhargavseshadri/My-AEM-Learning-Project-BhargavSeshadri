package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.DemoProductService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;


//BhargavSeshadri : This is SERVICE - 1 ; the implimented DemoProductService interface
@Component(service = DemoProductService.class, name = "ServiceOne")
@ServiceRanking(1000)
public class DemoProductModelServiceOneImpl implements DemoProductService{

    @Override
    public String returnName(){
        return "Demo ProductModel Service One Loaded";
    }
}
