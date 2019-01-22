package com.pitech.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Value("${files.path}")
    private String applicationFilesPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/files/**")
                .addResourceLocations("file:///" + applicationFilesPath);

        registry.addResourceHandler("/static/**")
                .addResourceLocations("/resources/", "/webjars/", "classpath:/static/")
                .resourceChain(true).addResolver(new WebJarsResourceResolver());

    }
}



