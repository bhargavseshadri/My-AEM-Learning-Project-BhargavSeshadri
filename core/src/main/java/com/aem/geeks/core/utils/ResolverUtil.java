package com.aem.geeks.core.utils;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;
import java.util.Map;

//bhargavseshadri -(only step) - creating the resourceResolver from ResourceResolverFactory using service user we have created in the previous steps
// (previous steps : 1)created a services user,  2) linked it to the bundle "APACHE SLING SERVICE USER ,APPER SERVICE AMENDMENT")


/**
 * resource resolver factory helper class
 */
public final class ResolverUtil {

    private ResolverUtil() {

    }

//	public static final String GEEKS_SERVICE_USER = "bhargavserviceuser";   //This user is not working properly, so created new one



    //Variable name we have given in the configuration while linking it to a bundle mapping
    public static final String GEEKS_SERVICE_USER = "seshadribhargavlatestserviceuser";

    public static ResourceResolver newResolver(ResourceResolverFactory resourceResolverFactory) throws LoginException {

        final Map<String, Object> paramMap = new HashMap<String, Object>();

        //"ResourceResolverFactory.SUBSERVICE" its just a constant you have to give
        paramMap.put(ResourceResolverFactory.SUBSERVICE, GEEKS_SERVICE_USER);

        // fetches the admin service resolver using service user.
        // we can write this everywhere we needed or we can write it in a function and use the function everywhere
        ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
        return resourceResolver;
    }

    //we can use this resolver to do multiple operations based on the permissions we have given to it
    //To see the example of using or calling this resourceResolver --> go to com/aem/geeks/core/services/impl/ComponentsInfoServiceImpl.java

}