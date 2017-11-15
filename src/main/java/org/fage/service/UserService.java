package org.fage.service;

import java.util.List;


import org.fage.domain.User;
import org.fage.redis.UserRedis;
import org.fage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @author Caizhfy
 * @email caizhfy@163.com
 * @createTime 2017年11月12日
 * @description 使用redisTemplate做复杂对象缓存
 *
 */
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserRedis userRedis;
	private static final String keyHead = "mysql:get:user:";
	
	/*
	 * 与cache做的一样的事情
	 */
	@Transactional
	public User save(User user){
		User newUser = userRepository.save(user);
		if(newUser!=null && newUser.getId()!=null)
			userRedis.add(keyHead + newUser.getId(), 1l, newUser);
		return newUser;
	}
	/*
	 * 与cache做的事情一样
	 */
	@Transactional
	public User update(User user){
		if(user!=null && user.getId()!=null)
			userRedis.delete(keyHead + user.getId()); 
		User newUser = userRepository.save(user);
		if(newUser!=null && newUser.getId()!=null)
			userRedis.add(keyHead + newUser.getId(), 1l, newUser);
		return null;
	}
	/*
	 * 与cache做的同样的事情
	 */
	@Transactional(readOnly = true)
	public User findById(Long id){
		User user = userRedis.get(keyHead + id);
		if(user==null){
			user = userRepository.findById(id);
			if(user != null)
				userRedis.add(keyHead + id, 1l, user);
		}
		return user;
	}
	
	@Transactional
	public List<User> findAll(){
		List<User> users = userRedis.getList("user:findAll");
		if(users==null||users.size()==0)
			users = userRepository.findAll();
		if(users!=null&&users.size()!=0)
			userRedis.add("user:findAll", 1L, users);
		return users;
	}
}
