package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.DemoService;
import com.aem.geeks.core.utils.ResolverUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Iterator;
import java.util.List;

@Component(service = DemoService.class,immediate = true)
public class DemoServiceAImpl implements DemoService {
    private static final Logger LOG= LoggerFactory.getLogger(DemoServiceAImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private SlingRepository slingRepository;

    @Activate
    public void activate(ComponentContext componentContext){
        LOG.info("\n ==============INSIDE ACTIVATE================");
        LOG.info("\n {} = {} ",componentContext.getBundleContext().getBundle().getBundleId(),componentContext.getBundleContext().getBundle().getSymbolicName());
    }

    @Deactivate
    public void deactivate(ComponentContext componentContext){
        LOG.info("\n ==============INSIDE DEACTIVATE================");
    }

    @Modified
    public void modified(ComponentContext componentContext){
        LOG.info("\n ==============INSIDE MODIFIED================");
    }


    //BhargavSeshadri -> example of using data-sly-list iterator : STEP: 1
    //For Step: 2 ->
    @Override
    public Iterator<Page>  getPages(){
        try {
            ResourceResolver resourceResolver= ResolverUtil.newResolver(resourceResolverFactory);
            PageManager pageManager=resourceResolver.adaptTo(PageManager.class);    //Here we are getting the PageManager
            Page page=pageManager.getPage("/content/aemgeeks/us/en");            //getting page using PageManager
            Iterator<Page> pages=page.listChildren();                               //getting the list of child pages and putting them in iterator
            return pages;                                                           //Sending the iterator obj
        } catch (LoginException e) {
            LOG.info("\n Login Exception {} ",e.getMessage());
        }
        return null;
    }

}