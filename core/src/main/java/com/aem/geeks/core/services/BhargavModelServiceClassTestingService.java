package com.aem.geeks.core.services;

import org.apache.sling.api.resource.Resource;

public interface BhargavModelServiceClassTestingService {
    String BhargavServiceMethod();
    String sendingToJavaClass();

    String sendingToJavaClassBundleContext();

    //Using Sling Model in this Service
    String ServiceGotTheModelData (Resource resource);

    String ServiceGotTheModelDataModelFactoryApproach(Resource resource);
}
