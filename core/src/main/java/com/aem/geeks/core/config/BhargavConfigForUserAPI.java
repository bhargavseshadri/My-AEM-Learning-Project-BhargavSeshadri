package com.aem.geeks.core.config;


import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


/*BhargavSeshadri - STEP: 1 - Creating OSGi configuration, so we can give the API related username/password/endpoints in the configuration.
* STEP: 2 : using this Config Values in a service : com/aem/geeks/core/services/impl/BhargavOsgiConfigServiceImpl.java
* */

@ObjectClassDefinition(name="Bhargav - USER API OGSI Config",
                       description = "This Config will take the required information about the API to get the response.")
public @interface BhargavConfigForUserAPI {

    @AttributeDefinition(
            name = "Login URL",
            type = AttributeType.STRING)
    String loginUrl() default "https://dummyjson.com/auth/login";

    @AttributeDefinition(
            name = "Protected User URL",
            type = AttributeType.STRING)
    String protectedUserUrl() default "https://dummyjson.com/auth/me";

    @AttributeDefinition(
            name = "Username",
            type = AttributeType.STRING)
    String Username();

    @AttributeDefinition(
            name = "Password",
            type = AttributeType.STRING)
    String password();

    @AttributeDefinition(
            name = "HTTP Timeout (ms)",
            type = AttributeType.INTEGER)
    int timeout() default 5000;

}
