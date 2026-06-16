package com.aem.geeks.core.servlets.tododropdown;

import com.adobe.granite.ui.components.ds.ValueMapResource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import java.util.HashMap;

//3 - Helper Class -- TodoDropdownOptionFactory -> class is responsible for converting our simple Java objects (like TodoItem) into Resource objects that Granite UI can understand and use as datasource options in the dropdown.
/*BhargavSeshadri : use of this class
* firstly, Granite UI cannot directly use simple Java objects or json response objsects as datasource options.
* so we have to convert the response json objects in to resource that Granite UI can understand and use as datasource options in the dropdown.
* Convert :: todoItem --> Resource , this is exactly what this class does.
* */

class TodoDropdownOptionFactory {


    Resource createDropdownOption(SlingHttpServletRequest request, String text, String value) {

        /*ValueMapDecorator : using this we can create a simple ValueMap instance backed by a HashMap.
        * It wraps a normal map and makes it behave like a ValueMap.*/
        ValueMap valueMap = new ValueMapDecorator(new HashMap<String, Object>());
        valueMap.put("text", text); // Granite Select uses : text  -> what user sees in dropdown
        valueMap.put("value", value); //value -> what gets stored

        /*ValueMapResource :  It allows us to create a Resource object without having an actual resource in the repository.
        * Here we dont have any physical resource node, we only have a memory of text = Learn Java, value = 1. But Granite UI still wants a Resource.
        * So Adobe provides this to create a fake/in-memory Resource.
        *
        * So after this line
        * Resource
            |
            +-- text = Learn Java
            |
            +-- value = 1
         */

        //new ResourceMetadata(): This is just an empty metadata object, we dont need to set anything in it for our use case. But Granite UI requires it as a parameter when creating a ValueMapResource.
        return new ValueMapResource(request.getResourceResolver(), new ResourceMetadata(), "nt:unstructured", valueMap);

        //This ValueMapResource constructor signature is ValueMapResource( ResourceResolver resourceResolver, ResourceMetadata metadata, String resourceType, ValueMap valueMap)
    }
}
