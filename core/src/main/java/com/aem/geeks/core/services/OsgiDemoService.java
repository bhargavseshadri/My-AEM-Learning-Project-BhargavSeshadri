package com.aem.geeks.core.services;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.List;

public interface OsgiDemoService {

    public List<String> getPagesTitle(Page page);
    public List<Page> getPages(ResourceResolver resourceResolver, String path);
    public Node getUpdatedNode(ResourceResolver resourceResolver, String path) throws RepositoryException;
}
