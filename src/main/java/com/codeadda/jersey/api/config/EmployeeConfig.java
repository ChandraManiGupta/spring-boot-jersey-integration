package com.codeadda.jersey.api.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.codeadda.jersey.api.controller.EmployeeController;

	@Component
	@ApplicationPath("/jersey")
public class EmployeeConfig extends ResourceConfig{
	 public EmployeeConfig() {
		  register(EmployeeController.class);
		 }
}
