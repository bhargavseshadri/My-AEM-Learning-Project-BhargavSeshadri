package com.aem.geeks.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;



/*BhargavSeshadri - Step:1 - OSGI Factory Configuration
* For Step:2 go to file ->  com/aem/geeks/core/services/impl/OSGiFactoryConfigImpl.java
* */

@ObjectClassDefinition(name="Bhargav - OSGi Factory Configuration",
        description = "OSGi Factory Configuration demo.")
public @interface GeeksOSGiFactoryConfig {
    @AttributeDefinition(
            name = "Config ID",
            description = "Enter service id.",
            type = AttributeType.INTEGER)
    int configID();

    @AttributeDefinition(
            name = "Service Name",
            description = "Enter service name.",
            type = AttributeType.STRING)
    public String serviceName() default "Service #";

    @AttributeDefinition(
            name = "Service URL",
            description = "Add Service URL.",
            type = AttributeType.STRING
    )
    String serviceURL() default "URL #";
}
