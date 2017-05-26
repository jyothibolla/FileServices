package com.finra.services.file;

import javax.servlet.MultipartConfigElement;

import com.finra.services.file.config.JerseyConfig;
import com.finra.services.file.service.MessageService;
import com.finra.services.file.webservice.FileResource;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Date: 05/23/17
 * Time: 18:03
 *
 * @author Jyothi Bolla
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="com.finra.services.file")
@ComponentScan(basePackageClasses = {FileResource.class, MessageService.class})
public class FileServiceApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(FileServiceApplication.class);
	}
	
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(FileServiceApplication.class, args);
		System.out.println("file-services");
        /*new SpringApplicationBuilder(FileServiceApplication.class)
                .showBanner(false)
                .run(args);*/
    }
    
    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/file-services/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
        return registration;
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("5120KB");
        factory.setMaxRequestSize("5120KB");
        return factory.createMultipartConfig();
    }
}
