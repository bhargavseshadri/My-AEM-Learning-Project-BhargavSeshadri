package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.models.BhargavTextProxyModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


/*BhargavSeshadri : This Sling Model is linked to the PROXY COMPONENT - apps/aemgeeks/components/bhargavtextproxy
* Mainly it is used to introduce the custom logic to the created Proxy Component*/
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = BhargavTextProxyModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BhargavTextProxyModelImpl implements BhargavTextProxyModel {

    @ValueMapValue
    private String text;

    @ValueMapValue
    private String customField;

    @Override
    public String getText() {
        return "From Model Printing the Text field : " + text;
    }

    @Override
    public String getCustomField() {
        return "From Model Printing the Custom field : " + customField;
    }
}
