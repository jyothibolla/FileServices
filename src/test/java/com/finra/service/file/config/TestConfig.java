package com.finra.service.file.config;

import com.finra.services.file.service.MessageService;
import com.finra.services.file.webservice.FileResource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {FileResource.class, MessageService.class})
public class TestConfig {
}
