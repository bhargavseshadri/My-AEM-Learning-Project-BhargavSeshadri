package com.aem.geeks.core.models.impl.compositemultifieldrltd.nestedcompmultifield;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


//NESTED COMPOSITE MULTIFIELD
// Step:4 -  This is a code to get hold of nested multifields data
@Model(adaptables = Resource.class)
public class Book {

    @ValueMapValue
    private String title;

    @ValueMapValue
    private int year;

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }
}
