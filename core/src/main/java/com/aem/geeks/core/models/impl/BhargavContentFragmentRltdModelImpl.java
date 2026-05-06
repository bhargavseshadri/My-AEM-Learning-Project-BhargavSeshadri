package com.aem.geeks.core.models.impl;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.aem.geeks.core.models.BhargavContentFragmentRltdModel;
import com.aem.geeks.core.services.impl.OSGiFactoryConfigImpl;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = BhargavContentFragmentRltdModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BhargavContentFragmentRltdModelImpl implements BhargavContentFragmentRltdModel {

    private static final Logger LOG = LoggerFactory.getLogger(BhargavContentFragmentRltdModelImpl.class);

    @SlingObject
    private Resource resource;

    @ValueMapValue
    private String fragmentPath;

    private String name;
    private String description;
    private double price;
    private ContentFragment cf;

    @PostConstruct
    protected void init() {

        ResourceResolver resolver = resource.getResourceResolver();
        Resource cfResource = resolver.getResource(fragmentPath);

        if (cfResource != null) {

            ContentFragment cf = cfResource.adaptTo(ContentFragment.class);

            if (cf != null) {
                name = cf.getElement("name").getContent();
                price = Double.parseDouble(cf.getElement("price").getContent());
            }
        }
    }

    @Override
    public String getAlLCfDetails() {
        name = cf.getElement("name").getContent();
        price = Double.parseDouble(cf.getElement("price").getContent());
        int quantity = Integer.parseInt(cf.getElement("quantity").getContent());
        description = cf.getDescription();

        return "Name: " + name + ", Price: ₹" + String.format("%,.0f", price) + ", Quantity: " + quantity + ", Description: " + description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getDiscountPrice() {
        return price * 0.9;
    }

    @Override
    public String getCategory() {
        return price > 50000 ? "Expensive" : "Affordable";
    }

    @Override
    public String getFormattedPrice() {
        return "₹" + String.format("%,.0f", price);
    }
}
