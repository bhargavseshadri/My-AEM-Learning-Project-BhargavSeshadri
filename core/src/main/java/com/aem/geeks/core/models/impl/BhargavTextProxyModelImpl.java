package com.aem.geeks.core.models.impl;

import com.adobe.cq.wcm.core.components.models.Text;
import com.aem.geeks.core.models.BhargavTextProxyModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;


/*BhargavSeshadri : This Sling Model is linked to the PROXY COMPONENT - apps/aemgeeks/components/bhargavtextproxy
 * Mainly it is used to introduce the custom logic to the created Proxy Component*/
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
        adapters = {BhargavTextProxyModel.class, Text.class},
        resourceType = "aemgeeks/components/bhargavtextproxy",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class BhargavTextProxyModelImpl implements BhargavTextProxyModel {

    @Self
    @Via(type = ResourceSuperType.class)
    private Text textDelegateObj;    //Object reference for core sling model

    @ValueMapValue
    private String customField;

    @Override
    public String getText() {
        return "From Model Printing the Text field (using delegation): " + textDelegateObj.getText();
    }

    @Override
    public String getCustomField() {
        return "From Model Printing the Custom field : " + customField;
    }
}
