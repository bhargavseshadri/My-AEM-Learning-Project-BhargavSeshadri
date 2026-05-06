package com.aem.geeks.core.models.impl.compositemultifieldrltd;

import com.aem.geeks.core.models.BhargavCompositeMultifieldModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = BhargavCompositeMultifieldModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BhargavCompositeMultifieldModelImpl implements BhargavCompositeMultifieldModel{

    @ChildResource(name = "compositemultifield")
    private List<BhargavCompositeMultifieldChildModel> items;

    @Override
    public List<BhargavCompositeMultifieldChildModel> getItems() {
        return items;
    }
}
