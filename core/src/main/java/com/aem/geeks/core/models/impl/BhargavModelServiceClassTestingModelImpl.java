package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.models.BhargavModelServiceClassTestingModel;
import com.aem.geeks.core.services.BhargavModelServiceClassTestingService;
import com.aem.geeks.core.utils.BhargavServiceInJava;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

// Service is used in java class in different ways and this model is just used to render the data.
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = BhargavModelServiceClassTestingModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BhargavModelServiceClassTestingModelImpl implements BhargavModelServiceClassTestingModel {

    private static final Logger LOG = LoggerFactory.getLogger(BhargavModelServiceClassTestingModelImpl.class);

    @ValueMapValue
    private String checkingField;

    @OSGiService
    private BhargavModelServiceClassTestingService bhargavModelServiceClassTestingService;

    private BhargavServiceInJava bhargavServiceInJava;

    @PostConstruct
    protected void init() {
        bhargavServiceInJava = new BhargavServiceInJava(bhargavModelServiceClassTestingService);
    }


    @Override
    public String compWorkingCheck() {
        return checkingField;
    }


    /*Step-1 - Constructor APPROACH - Sending the service to java class and then using the java class method here.

    We get the DATA here in this flow  -- this model sends the service obj in constructor ---> and the java class uses the service obj in the class --> and here
    we are using the java obj and rendering the data*/
    @Override
    public String getDataFromJavaClass() {

        LOG.info("Got the Data From Service -> Java -> Model {}", bhargavServiceInJava.getServiceData());

        return bhargavServiceInJava.getServiceData() + " Received in Model and Sending to rendering";
    }

    @Override
    public String getDataFromJavaClassBundleContextApproach() {
        LOG.info("Got the Data From Service -> Java -> Model {}", bhargavServiceInJava.getServiceDataUsingBundleContext());

        return bhargavServiceInJava.getServiceDataUsingBundleContext() + " Received in Model and Sending to rendering";
    }


}
