package com.aem.geeks.core.services;


import org.apache.sling.api.resource.Resource;

import javax.jcr.RepositoryException;

public interface BhargavPropertyBtnService {
    boolean addProperty(Resource resource) throws RepositoryException;
}
