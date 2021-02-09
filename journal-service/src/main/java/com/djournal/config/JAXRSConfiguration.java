package com.djournal.config;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import io.swagger.jaxrs.config.BeanConfig;

/**
 * Configures a JAX-RS endpoint.
 */
@ApplicationPath("/api")
public class JAXRSConfiguration extends Application {
	
	 public JAXRSConfiguration(@Context ServletConfig servletConfig) {
	        BeanConfig beanConfig = new BeanConfig();
	        beanConfig.setVersion("1.0.0");
	        beanConfig.setTitle("Dietary Journal API");
	        beanConfig.setBasePath("/journal-service/api");
	        beanConfig.setResourcePackage("com.djournal.boundary");
	        beanConfig.setScan(true);
	    }
}
