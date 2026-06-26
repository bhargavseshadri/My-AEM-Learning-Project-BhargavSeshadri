package com.aem.geeks.core.models;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import com.adobe.cq.wcm.core.components.models.Text;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;




/*BhargavSeshadri - Step: 2 (last step)- Sling Model
*
* For Step: 1 - Create Component : apps/aemgeeks/components/bhargavtextproxy-two*/

@Model(
        adaptables = {Resource.class,SlingHttpServletRequest.class},
        adapters = Text.class,
        resourceType = "aemgeeks/components/bhargavtextproxy-two" //this is important for this model tto work.
)
public class BhargavTextProxyTwoModel implements Text{

    @Self
    @Via(type = ResourceSuperType.class)  //here it is going to the core text component and getting this property
    private Text textDelegate;  //@Via(ResourceSuperType.class) means  Adapt the super resource type instead of current component.

    @Override
    public String getText() {

        String originalText = textDelegate.getText();

        if (originalText != null) {
            return originalText + " - This is added by using delegation, Bhargav";
        }

        return null;
    }


    /*
        Remaining methods delegated manually
     */

    @Override
    public boolean isRichText() {
        return textDelegate.isRichText();
    }

    @Override
    public String getId() {
        return textDelegate.getId();
    }

    @Override
    public String getExportedType() {
        return textDelegate.getExportedType();
    }

}
