package com.codeadda.jersey.api.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codeadda.jersey.api.dao.IEmployeeRepository;
import com.codeadda.jersey.api.error.ErrorConstants;
import com.codeadda.jersey.api.error.ErrorResponse;
import com.codeadda.jersey.api.model.Employee;

@Component
@Path("/employee/api")
public class EmployeeController {

	@Autowired
	private IEmployeeRepository repository;
	ErrorResponse errorResponse = new ErrorResponse();

	@GET
	@Produces("application/json")
	@Path("/employees")
	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	@GET
	@Produces("application/json")
	@Path("/emp/{empId}")
	public Response getEmployeeById(@PathParam("empId") int empId) {
		boolean isPresent = repository.findById(empId).isPresent();

		if (isPresent) {
			Employee employee = repository.findById(empId).get();
			return Response.status(Response.Status.ACCEPTED).entity(employee).build();
		} else {
			errorResponse.setErrorCode(ErrorConstants.FAILURE_CODE);
			errorResponse.setErrorDescription("Employee with employee id: " + empId + " not found");
			errorResponse.setStatus(ErrorConstants.FAILURE);
			return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
		}
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/addEmp")
	public Response createEmployee(Employee employee) {
		Employee emp = repository.save(employee);
		return Response.status(Response.Status.CREATED).entity(emp).build();
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateEmp/{empId}")
	public Response updateEmployee(Employee updatedEmpDetails ,@PathParam("empId") int empId) {
		boolean isPresent = repository.findById(empId).isPresent();
		if (isPresent) {
			Employee employee = repository.findById(empId).get();
			employee.setEmpId(updatedEmpDetails.getEmpId());
			employee.setEmpOrganization(updatedEmpDetails.getEmpOrganization());
			employee.setEmpName(updatedEmpDetails.getEmpName());
			repository.saveAndFlush(employee);
			return Response.status(Response.Status.ACCEPTED).entity(employee).build();

		} else {
			errorResponse.setErrorCode(ErrorConstants.FAILURE_CODE);
			errorResponse.setErrorDescription("Employee with employee id: " + empId + " not found to update");
			errorResponse.setStatus(ErrorConstants.FAILURE);
			return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
		}
	}
	

	@DELETE
	@Produces("application/json")
	@Path("/deleteEmp/{empId}")
	public Response deleteEmployee(@PathParam("empId") int empId) {
		boolean isPresent = repository.findById(empId).isPresent();
		if (isPresent) {
			repository.deleteById(empId);
			errorResponse.setErrorCode(ErrorConstants.SUCCESS_CODE);
			errorResponse.setErrorDescription("Employee with employee id: " + empId + " deleted successfully");
			errorResponse.setStatus(ErrorConstants.SUCCESS);
			return Response.status(Response.Status.ACCEPTED).entity(errorResponse).build();
		} else {
			errorResponse.setErrorCode(ErrorConstants.FAILURE_CODE);
			errorResponse.setErrorDescription("Employee with employee id: " + empId + " not found");
			errorResponse.setStatus(ErrorConstants.FAILURE);
			return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
		}

	}

}
