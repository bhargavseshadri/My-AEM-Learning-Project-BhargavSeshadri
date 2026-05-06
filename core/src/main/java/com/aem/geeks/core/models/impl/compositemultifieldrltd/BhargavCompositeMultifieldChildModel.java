package com.aem.geeks.core.models.impl.compositemultifieldrltd;

import com.aem.geeks.core.models.BhargavCompositeMultifieldModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BhargavCompositeMultifieldChildModel {

    @ValueMapValue
    private String question;

    @ValueMapValue
    private String answer;

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
