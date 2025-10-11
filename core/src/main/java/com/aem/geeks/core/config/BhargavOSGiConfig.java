package com.aem.geeks.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;


//BhargavSeshadri - STEP:1 - Normal OSGI Configuration

@ObjectClassDefinition(name="Bhargav - Normal OSGI Configuration",
        description = "Normal OSGi Configuration demo.")  //this @ObjectClassDefinition annotation will take two thing name and description.
public @interface BhargavOSGiConfig {

    //Below we are defining the fields which should show in the configuration

    @AttributeDefinition(
            name = "Phone Number",
            description = "Enter Phone Number.",
            type = AttributeType.INTEGER)
    int phoneNumber();

    @AttributeDefinition(
            name = "Name",
            description = "Enter name.",
            type = AttributeType.STRING)
    public String name() default "Seshadri";

    @AttributeDefinition(
            name = "Fav Movie",
            description = "Enter the fav movie.",
            type = AttributeType.STRING
    )
    String favMovie() default "The Godfather";

    @AttributeDefinition(
            name = "Countries",
            description = "Add countries locales for which you want to run this service.",
            type = AttributeType.STRING
    )
    String[] getCountries() default {"de","in"};   //ARRAY

    @AttributeDefinition(
            name = "Run Modes",
            description = "Select Run Mode.",
            options = {
                    @Option(label = "Author",value = "author"),
                    @Option(label = "Publish",value = "publish"),
                    @Option(label = "Both",value = "both")
            },
            type = AttributeType.STRING)
    String getRunMode() default "both";   //dropdown
}
