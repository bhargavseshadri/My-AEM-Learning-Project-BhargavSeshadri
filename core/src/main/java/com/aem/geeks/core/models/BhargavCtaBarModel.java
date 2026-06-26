package com.aem.geeks.core.models;


import com.adobe.cq.wcm.core.components.models.ListItem;
import com.adobe.cq.wcm.core.components.models.Teaser;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import java.util.List;





/*SeshadriBhargav - bhargav-ctabar-proxycomp
* Step : 2 - Creating a model and using delegation concept getting the fields values from core component.
* Step : 3(last step) - HTL - apps/aemgeeks/components/bhargav-ctabar-proxycomp/bhargav-ctabar-proxycomp.html
* Step : 1 - Create the comp - apps/aemgeeks/components/bhargav-ctabar-proxycomp/_cq_dialog/.content.xml
* */
@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        adapters = Teaser.class,
        resourceType = BhargavCtaBarModel.BHARGAV_RESOURCE_TYPE, //this is important for this model to work.
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class BhargavCtaBarModel implements Teaser {

    protected static final String BHARGAV_RESOURCE_TYPE = "aemgeeks/components/bhargav-ctabar-proxycomp";

    //This is Sling model - DELEGATION
    @Self
    //Here @Self work is to take the current adaptable and adapt it to whatever type this field is, here the field is Teaser.
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
