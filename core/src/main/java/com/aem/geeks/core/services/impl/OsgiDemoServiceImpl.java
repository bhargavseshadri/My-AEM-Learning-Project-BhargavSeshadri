package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.OsgiDemoService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

@Component(service = OsgiDemoService.class)
public class OsgiDemoServiceImpl implements OsgiDemoService {


    //This method returns a list of title os all the pages
    //Simple Iterates all the child pages of a given page and puts the child pages title in to list and returns it
    @Override
    public List<String> getPagesTitle(Page page) {
        List<String> pageList = new ArrayList<>();

        Iterator<Page> pages = page.listChildren();
        while (pages.hasNext()) {
            Page child = pages.next();
            pageList.add(child.getTitle());
        }
        return pageList;
    }


    //In this method using the resourceResolver and path we get that resource --> and adaptTo as a page of that resource
    //Then it will iterate all the child pages of that page and return the list of child pages
    //String path -> this is a page path which have child pages
    @Override
    public List<Page> getPages(ResourceResolver resourceResolver, String path) {
        List<Page> pageList = new ArrayList<>();
        Page parentPage = resourceResolver.getResource(path).adaptTo(Page.class);
        Iterator<Page> pages = parentPage.listChildren();
        while (pages.hasNext()) {
            Page child = pages.next();
            pageList.add(child);
        }
        return pageList;

    }

    @Override
    public Node getUpdatedNode(ResourceResolver resourceResolver, String path) throws RepositoryException {
        Node parentNode = resourceResolver.getResource(path).adaptTo(Node.class);
        parentNode.setProperty("updatedBy", resourceResolver.getUserID());
        return parentNode;
    }
}
