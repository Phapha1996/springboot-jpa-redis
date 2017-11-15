package org.fage.repository.test;

import org.fage.domain.Department;
import org.fage.domain.User;
import org.fage.repository.UserRepository;
import org.fage.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUserRepository {
	@Autowired
	UserRepository repository;
	@Autowired
	UserService service;
	
	@Test
	public void testGet(){
		System.out.println(repository.findById(1).toString());
	}

	@Test
	public void testGetAll(){
		System.out.println(repository.findAll());
	}
	
	/*@Test
	public void testServiceGet(){
		System.out.println(service.findById(2));
	}
	
	@Test
	public void testFindAll(){
		System.out.println(service.findAll().size());
	}
	
	@Test
	public void testsave(){
		User u = new User();
		u.setUsername("qweqwe");
		Department d = new Department();
		d.setName("帅哥部门");
		u.setDepartment(d);
		service.saveOrUpdate(u);
		System.out.println(u.getId());
	}
	
	@Test
	public void testSave2(){
		User u = service.findById(17);
		System.out.println(u.getId());
		System.out.println(u.getDepartment());
	}*/
}
