package com.aem.geeks.core.models;


import com.adobe.cq.wcm.core.components.models.ListItem;
import com.adobe.cq.wcm.core.components.models.Teaser;
import com.adobe.cq.wcm.core.components.models.Text;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import java.util.List;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        adapters = Teaser.class,
        resourceType = BhargavCtaBarModel.BHARGAV_RESOURCE_TYPE, //this is important for this model tto work.
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class BhargavCtaBarModel implements Teaser{

    protected static final String BHARGAV_RESOURCE_TYPE = "aemgeeks/components/bhargav-ctabar-proxycomp";

    @Self
    @Via(type = ResourceSuperType.class)
    private Teaser teaser;


//------Our Custom Fields
    @ValueMapValue
    private String backgroundColor;

    @ValueMapValue
    private String textColor;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }
//---------------------------




    /*
        Core component methods, delegated manually
     */
    @Override
    public String getTitle() {
        return teaser.getTitle();
    }

    @Override
    public String getDescription() {
        return teaser.getDescription();
    }

    @Override
    public List<ListItem> getActions() {
        return teaser.getActions();
    }

    @Override
    public boolean isActionsEnabled() {
        return teaser.isActionsEnabled();
    }

    @Override
    public String getPretitle() {
        return teaser.getPretitle();
    }

    @Override
    public String getTitleType() {
        return teaser.getTitleType();
    }
//
//    @Override
//    public String getId() {
//        return teaser.getId();
//    }

    @Override
    public String getExportedType() {
        return BHARGAV_RESOURCE_TYPE;
    }
}
