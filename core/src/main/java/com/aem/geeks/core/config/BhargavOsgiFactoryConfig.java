package com.aem.geeks.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "Country Configuration"
)
public @interface BhargavOsgiFactoryConfig {

    @AttributeDefinition(name = "Country")
    String country();

    @AttributeDefinition(name = "API URL")
    String apiUrl();

    @AttributeDefinition(name = "API Key")
    String apiKey();

    @AttributeDefinition(name = "Enabled")
    boolean enabled() default true;
}
