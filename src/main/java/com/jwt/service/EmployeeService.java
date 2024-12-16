package com.jwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jwt.model.Employee;

@Service
public class EmployeeService {

	private List<Employee> employees=new ArrayList<>();
	
	public EmployeeService()
	{
		employees.add( new Employee(UUID.randomUUID().toString(),"Abrar","abrark213@gmai.com"));
		employees.add(new Employee(UUID.randomUUID().toString(),"Abrarkhan","abrar123@gmail.com"));
	}
	
	public List<Employee> getListOfEmployee()
	{
		return  employees;
	}
}
