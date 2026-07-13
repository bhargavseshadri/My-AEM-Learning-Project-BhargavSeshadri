package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.models.BhargavModelServiceClassTestingModel;
import com.aem.geeks.core.models.impl.BhargavModelServiceClassTestingModelImpl;
import com.aem.geeks.core.services.BhargavModelServiceClassTestingService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


//Step: a - Create a Service
@Component(service = BhargavModelServiceClassTestingService.class)
public class BhargavModelServiceClassTestingServiceImpl implements BhargavModelServiceClassTestingService {

    @Override
    public String BhargavServiceMethod() {
        return "This is a rough service method";
    }

    @Override
    public String sendingToJavaClass() {
        return "Constructor APPROACH :: Service Sending Data -> ";
    }

    @Override
    public String sendingToJavaClassBundleContext() {
        return "Bundle Context APPROACH :: 1. Service Sending Data -> ";
    }

/**************************************Getting MODEL data in this SERVICE****************************************************************************/

    //Model to Service -- Approach : 1 (Using Resource)-- Step: b - Getting Sling Model in this Service
    //Approach - 1 - Step: c (last step)- render it in sling model - BhargavModelServiceClassTestingModelImpl.java
    /*
    --> Resource gives these to us.
    * Resource = TemplatedResourceImpl,
    * type=aemgeeks/components/content/bhargav-model-service-class-testing,
    * path=/content/aemgeeks/us/en/delete-later/jcr:content/root/responsivegrid/bhargav_model_servic
    * */
    @Override
    public String ServiceGotTheModelData(Resource resource){
        BhargavModelServiceClassTestingModelImpl bhargavModelServiceClassTestingModelImpl = resource.adaptTo(BhargavModelServiceClassTestingModelImpl.class);
        return bhargavModelServiceClassTestingModelImpl.sendingDataToService() + "--> Received the Data from Model. " + "Resource = "+ resource.getPath();

    }


    //Model to Service -- Approach : 2 (Model Factory Approach) -- Step: a - Getting Model Factory
    @Reference
    private ModelFactory modelFactory;

    //Model to Service -- Approach : 2 (Model Factory Approach) -- Step: b - using Model Factory
    // Step: c -- rendering - BhargavModelServiceClassTestingModelImpl.java
    @Override
    public String ServiceGotTheModelDataModelFactoryApproach(Resource resource) {
        try {
            BhargavModelServiceClassTestingModelImpl model = modelFactory.createModel(resource, BhargavModelServiceClassTestingModelImpl.class);
            return model.sendingDataToService() + "--> Received the Data from Model(Model Factory Approach). ";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
