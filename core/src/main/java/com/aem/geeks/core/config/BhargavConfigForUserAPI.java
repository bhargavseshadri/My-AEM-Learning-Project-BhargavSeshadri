package com.aem.geeks.core.config;


import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


/*BhargavSeshadri - STEP: 1 - Fetching DATA from API which needs ACCESS TOKEN
* Here creating OSGi configuration, so we can give the API related username/password/endpoints in the configuration.
*
* STEP: 2 : using this Config Values in a service : com/aem/geeks/core/services/impl/BhargavOsgiConfigServiceImpl.java
* */

@ObjectClassDefinition(name="Bhargav - USER API OGSI Config",
                       description = "This Config will take the required information about the API to get the response.")
public @interface BhargavConfigForUserAPI {

    @AttributeDefinition(
            name = "Login URL",
            type = AttributeType.STRING)
    String loginUrl() default "https://dummyjson.com/auth/login";   // using this endpoint we get the access token using the username and password creds

    @AttributeDefinition(
            name = "Protected User URL",
            type = AttributeType.STRING)
    String protectedUserUrl() default "https://dummyjson.com/auth/me";  //With the access token we hit this endpoint to get the user data response from API.

    @AttributeDefinition(
            name = "Username",
            type = AttributeType.STRING)
    String Username();                       //Username and password we get from the Dummy users endpoint "https://dummyjson.com/users"

    @AttributeDefinition(
            name = "Password",
            type = AttributeType.STRING)
    String password();

}
