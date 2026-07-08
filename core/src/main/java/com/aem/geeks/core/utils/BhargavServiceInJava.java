package com.aem.geeks.core.utils;

import com.aem.geeks.core.services.BhargavModelServiceClassTestingService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class BhargavServiceInJava {

    private BhargavModelServiceClassTestingService bhargavModelServiceClassTestingService;

    public BhargavServiceInJava(BhargavModelServiceClassTestingService bhargavModelServiceClassTestingService){
        this.bhargavModelServiceClassTestingService = bhargavModelServiceClassTestingService;
    }


    /*Step: 2 - Constructor APPROACH -
    Here we get the hold of service, because "BhargavModelServiceClassTestingModelImpl" this model send that service
                as a constructor parameter while creating OBJ.*/
    public String getServiceData() {
        return bhargavModelServiceClassTestingService.sendingToJavaClass() + " Java Class Received Data -> ";
    }


    /*Step: 1 - Bundle Context Approach
    * Here using the bundle context we are getting hold of the service class*/
    public String getServiceDataUsingBundleContext() {

        //getting bundle context
        BundleContext bundleContext = FrameworkUtil.getBundle(BhargavServiceInJava.class).getBundleContext();

        // Get ServiceReference
        ServiceReference<BhargavModelServiceClassTestingService> reference = bundleContext.getServiceReference(BhargavModelServiceClassTestingService.class);

        // Get actual service
        BhargavModelServiceClassTestingService service = bundleContext.getService(reference);

        //using the service logic now
        return service.sendingToJavaClassBundleContext() + " 2.Java Class Received Data(bundle context) -> ";

        //after the work Release the service (good practice)
        //bundleContext.ungetService(reference);
    }


}
