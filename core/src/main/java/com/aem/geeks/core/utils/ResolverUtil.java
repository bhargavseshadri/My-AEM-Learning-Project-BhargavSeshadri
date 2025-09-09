package com.aem.geeks.core.utils;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;
import java.util.Map;

//bhargavseshadri -(only step) - creating the resourceResolver using service user we have created in the pevious steps (previous steps : 1)created a services user,  2) linked it to the bundle "APACHE S;LING SERVICE USER ,APPER SERVICE AMENDMENT")



/**
 *  resource resolver factory helper class
 */
public final class ResolverUtil {

    private ResolverUtil() {

    }

	public static final String GEEKS_SERVICE_USER = "bhargavserviceuser";   //Variable name we have given in the configuration while linking it to a bundle mapping
    /**
     * @param  resourceResolverFactory factory
     * @return new resource resolver for bhargavseshadri-user service user
     * @throws LoginException if problems
     */
    public static ResourceResolver newResolver( ResourceResolverFactory resourceResolverFactory ) throws LoginException {
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put( ResourceResolverFactory.SUBSERVICE, GEEKS_SERVICE_USER ); //"ResourceResolverFactory.SUBSERVICE" its just a constant you have to give

        // fetches the admin service resolver using service user.
        ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(paramMap);   // we can write this everywhere we needed or we can write it in a function and use the function everywhere
        return resolver;
    }

    //we can use this resolver to do multiple operations based on the permitions we have given to it

    //To see the example of using or calling this resourceResolver --> go to com/aem/geeks/core/services/impl/ComponentsInfoServiceImpl.java
	
}