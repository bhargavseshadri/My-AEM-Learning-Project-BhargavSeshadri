package com.aem.geeks.core.models.impl.compositemultifieldrltd.nestedcompmultifield;

import com.aem.geeks.core.models.BhargavNestedCompMultifieldModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import java.util.List;



//NESTED COMPOSITE MULTIFIELD
// Step:2 -  Main Sling Model for nested multifield - from here we traverse through the multifield and nested multifield.
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = BhargavNestedCompMultifieldModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BhargavNestedMultifieldModelImpl implements BhargavNestedCompMultifieldModel {

    @ChildResource
    private List<Author> authors;

    @Override
    public List<Author> getAuthors() {
        return authors;
    }
}
