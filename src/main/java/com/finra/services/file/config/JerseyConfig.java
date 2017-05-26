package com.finra.services.file.config;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Configuration
@Component
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
 
    	property(ServerProperties.RESOURCE_VALIDATION_IGNORE_ERRORS, true);
    	
        //Glassfish multipart file uploader feature
    	packages("org.glassfish.jersey.examples.multipart");
        register(MultiPartFeature.class);
        register(JacksonFeature.class);
        register(RequestContextFilter.class);
        packages("com.finra.services.file.webservice");
        register(LoggingFilter.class);
        
        
    }
}