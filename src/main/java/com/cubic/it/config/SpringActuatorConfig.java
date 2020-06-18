package com.cubic.it.config;

import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableConfigurationProperties
@Configuration
@EnableAutoConfiguration
@Import(EndpointAutoConfiguration.class)
public class SpringActuatorConfig extends WebMvcConfigurerAdapter{

}