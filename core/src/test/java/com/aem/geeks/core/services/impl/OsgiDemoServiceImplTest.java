package com.aem.geeks.core.services.impl;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//bhargavSeshadri - Reference for writing Junits for Services

@ExtendWith(AemContextExtension.class)
class OsgiDemoServiceImplTest {

    //changing the AemContext from sling Api to Jcr Api
    AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

    OsgiDemoServiceImpl osgiDemoService = new OsgiDemoServiceImpl();

    Page page;

    //Getting the ResourceResolver
    ResourceResolver resourceResolver = aemContext.resourceResolver();

    @BeforeEach
    void setUp() {
        //Creating Dummy Pages
        //Parent page
        page = aemContext.create().page("/content/bhargav/us/en", "templateBhargav", "BhargavPage");

        //Child pages
        aemContext.create().page("/content/bhargav/us/en/child1", "templateBhargav", "First-Child");
        aemContext.create().page("/content/bhargav/us/en/child2", "templateBhargav", "Second-Child");
    }

    @Test
    void getPagesTitle() {
        //Calling the actual java method
        List<String> pagesTitle = osgiDemoService.getPagesTitle(page);

        //Assertions
        assertEquals(2, pagesTitle.size());
        assertEquals("First-Child", pagesTitle.get(0));
        assertEquals("Second-Child", pagesTitle.get(1));
    }

    @Test
    void getPages() {
        //Calling the actual java method
        List<Page> bhargavPages = osgiDemoService.getPages(resourceResolver, "/content/bhargav/us/en");

        //Assertions
        assertEquals(2, bhargavPages.size());
        assertEquals("First-Child", bhargavPages.get(0).getTitle());
        assertEquals("child2", bhargavPages.get(1).getName());
    }

    @Test
    void getUpdatedNode() throws RepositoryException {

        //So here we are doing JCR Changes, so aemContext will defaultly give sling API, so before coming here change the aemContext to JCR Api.

        Node node = osgiDemoService.getUpdatedNode(resourceResolver, "/content/bhargav/us/en");

        //So here in the assertion we can check what property we have added.
        //admin - lets say admin is the userId.
        assertEquals("admin", node.getProperty("updatedBy").getString());
    }
}