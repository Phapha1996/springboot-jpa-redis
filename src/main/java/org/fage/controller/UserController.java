package org.fage.controller;

import java.util.List;

import org.fage.domain.User;
import org.fage.redis.UserRedis;
import org.fage.repository.UserRepository;
import org.fage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author Caizhfy
 * @email caizhfy@163.com
 * @createTime 2017年11月10日
 * @description 
 *
 */
@RestController
@RequestMapping("/usr")
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/get/{id}")
	public User get(@PathVariable long id){
		return userService.findById(id);
	}
	
	@RequestMapping(value = "/add")
	public User add(User u){
		return userService.save(u);
	}
	
	@RequestMapping("/list")
	public List<User> list(){
		return userService.findAll();
	}
	
	@RequestMapping("/hello")
	public String hello(){
		return "success";
	}
	
}
