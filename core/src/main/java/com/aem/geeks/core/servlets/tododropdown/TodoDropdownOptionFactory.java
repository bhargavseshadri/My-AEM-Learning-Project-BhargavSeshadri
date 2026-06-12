package com.aem.geeks.core.servlets.tododropdown;

import com.adobe.granite.ui.components.ds.ValueMapResource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import java.util.HashMap;

class TodoDropdownOptionFactory {

    private static final String GRANITE_OPTION_RESOURCE_TYPE = "nt:unstructured";

    Resource create(SlingHttpServletRequest request, String text, String value) {
        ValueMap valueMap = new ValueMapDecorator(new HashMap<String, Object>());
        valueMap.put("text", text);
        valueMap.put("value", value);

        return new ValueMapResource(request.getResourceResolver(), new ResourceMetadata(), GRANITE_OPTION_RESOURCE_TYPE, valueMap);
    }
}
