package com.aem.geeks.core.models.impl.compositemultifieldrltd.nestedcompmultifield;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;


//NESTED COMPOSITE MULTIFIELD
// Step:3 -  This is a code to get hold of normal composite multifields data and from here we goto nested multifield
@Model(adaptables = Resource.class)
public class Author {

    @ValueMapValue
    private String name;

    @ValueMapValue
    private String email;

    @ChildResource
    private List<Book> books; //For nested Multifield

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Book> getBooks() {
        return books;
    }
}
