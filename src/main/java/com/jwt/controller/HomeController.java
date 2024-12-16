package com.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.model.Employee;
import com.jwt.service.EmployeeService;

@RestController
@RequestMapping("/welcome")
public class HomeController {

	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping("/employees")
	public List<Employee> getListOfEmployee()
	{
		return employeeService.getListOfEmployee(); 
	}
}
