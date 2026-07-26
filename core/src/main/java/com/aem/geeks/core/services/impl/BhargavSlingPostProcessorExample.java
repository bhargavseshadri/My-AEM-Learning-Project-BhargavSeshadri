package com.aem.geeks.core.services.impl;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.servlets.post.Modification;
import org.apache.sling.servlets.post.SlingPostProcessor;
import org.osgi.service.component.annotations.Component;


/*BhargavSeshadri - SlingPostProcessor example
*this code will work when we try to submit without giving "personName" value for "bhargav-backend-aem-apis-comp" dialog. and it also add hello + personName.'
* This is only step and it work automatically. when we try to submit the dialog.
 */
@Component(service = SlingPostProcessor.class)
public class BhargavSlingPostProcessorExample implements SlingPostProcessor {

    private static final String PERSON_NAME = "./personName";

    @Override
    public void process(SlingHttpServletRequest request, List<Modification> modifications) throws Exception {

        RequestParameter parameter = request.getRequestParameter(PERSON_NAME);

        if (parameter == null || parameter.getString().trim().isEmpty()) {
            throw new IllegalArgumentException("Person Name is mandatory.");
        }

        String updatedName = "Hello " + parameter.getString().trim();

        Resource resource = request.getResource();

        ModifiableValueMap properties = resource.adaptTo(ModifiableValueMap.class);

        if (properties != null) {
            properties.put("personName", updatedName);
        }
    }
}