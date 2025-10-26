package com.aem.geeks.core.models.impl.apirelated;


import com.aem.geeks.core.models.UserAPISlingModel;
import com.aem.geeks.core.services.UserAPIService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;




/*BhargavSeshadri - STEP: 3 - Creating  a Sling Model So using that we can render the API info on page
 * For Step: 2 Go to -> com/aem/geeks/core/services/impl/UserAPIServiceImpl.java
 * For STEP: 4 go to -> apps/aemgeeks/components/content/bhargavapicomp/bhargavapicomp.html
 * */

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = UserAPISlingModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UserAPISlingModelImpl implements UserAPISlingModel {

    @SlingObject
    private SlingHttpServletRequest request;

    @OSGiService
    private UserAPIService userAPIService;

    private String userJsonString;

    @PostConstruct
    protected void init() {
        try {
            JsonNode node = userAPIService != null ? userAPIService.getMeData() : null;
            if (node != null) {
                ObjectMapper mapper = new ObjectMapper();
                userJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
            } else {
                userJsonString = "{\"error\":\"no-data\"}";
            }
        } catch (Exception e) {
            userJsonString = "{\"error\":\"exception\",\"message\":\"" + e.getMessage() + "\"}";
        }
    }

    @Override
    public String getMeJsonString() {
        return userJsonString;
    }
}
