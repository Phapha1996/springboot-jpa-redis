package org.fage.controller;

import org.fage.domain.Department;
import org.fage.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dep")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping("/get/{id}")
	public Department get(@PathVariable long id){
		return departmentService.findById(id);
	}
	
	@RequestMapping("/new")
	public Department save(Department department){
		return departmentService.create(department);
	}
	
}
