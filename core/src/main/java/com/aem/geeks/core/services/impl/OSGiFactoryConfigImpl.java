package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.config.GeeksOSGiFactoryConfig;
import com.aem.geeks.core.services.OSGiFactoryConfig;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/*BhargavSeshadri - STEP:2 - Service For OSGI Factory Configuration
* For - STEP:1 Go to -> com/aem/geeks/core/config/GeeksOSGiFactoryConfig.java
* For - STEP:3 Go to -> com/aem/geeks/core/models/impl/ProductModelImpl.java
* */

@Component (service = OSGiFactoryConfig.class,configurationPolicy = ConfigurationPolicy.REQUIRE)      //here this OSGiFactoryConfig.class is just the interface for this service
@Designate (ocd = GeeksOSGiFactoryConfig.class, factory = true)                                     //factory = true - makes the normal configuration in to factory configuration and @Designate will designate our config to this service
public class OSGiFactoryConfigImpl implements OSGiFactoryConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(OSGiFactoryConfigImpl.class);

    private int configID;
    private String serviceName;
    private String serviceURL;

    private List<OSGiFactoryConfig> configsList;        //IMPORTANT - here we are creating one list and we put all the factory config instances in to this list and then we pass this

    @Activate
    @Modified
    protected void activate(final GeeksOSGiFactoryConfig config) {
        configID = config.configID();
        serviceName=config.serviceName();
        serviceURL=config.serviceURL();
    }

    @Reference(service = OSGiFactoryConfig.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void bindOSGiFactoryConfig(final OSGiFactoryConfig config) {    //Bind Method - here we are binding our service to this method
        if (configsList == null){
            configsList = new ArrayList<>();  //here we are creating a new list, so whenever the configuration is created or available at that time it will create this new list
        }
        configsList.add(config);        //adding our configurations to the list, and after the first time for the next set of configurations it will store to this list

    }

    public void unbindOSGiFactoryConfig(final OSGiFactoryConfig config) {   //when we remove the configuration then this unbind method will trigger and remove that config from the list
        configsList.remove(config);
    }

    @Override
    public int getConfigID() {
        return configID;
    }
    @Override
    public String getServiceName() {
        return serviceName;
    }
    @Override
    public String getServiceURL() {
        return serviceURL;
    }


    @Override
    public List<OSGiFactoryConfig> getAllConfigs(){  //here in this method we are returning our list
        return configsList;
    }












    @Override
    public OSGiFactoryConfig get(int configID) {   //for now dont consider this method
        for (OSGiFactoryConfig confFact : configsList) {
            if (configID==confFact.getConfigID())
                return confFact;
        }
        return null;
    }
}
