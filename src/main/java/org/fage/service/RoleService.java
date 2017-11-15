package org.fage.service;

import org.fage.domain.Role;
import org.fage.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Caizhfy
 * @email caizhfy@163.com
 * @createTime 2017年11月13日
 * @description 使用cache注解
 *
 */
@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;

	/*
	 * 该注解为存取缓存，如果缓存中有则从中取； 如果没有，则从数据库取出，在存入缓存，然后返回给用户
	 */
	@Cacheable(value = "mysql:role", keyGenerator = "simpleKey")
	public Role findById(Long id) {
		return roleRepository.findOne(id);
	}

	/*
	 * 该注解为修改缓存，如果不存在则创建 存在则按需修改
	 */
	@CachePut(value = "mysql:role", keyGenerator = "objectId")
	public Role create(Role role) {
		return roleRepository.save(role);
	}

	/*
	 * 该注解为修改缓存，如果不存在则创建 存在则按需修改
	 */
	@CachePut(value = "mysql:role", keyGenerator = "objectId")
	public Role update(Role role) {
		return roleRepository.save(role);
	}

	/*
	 * 删除缓存，从数据库中删除数据，如果缓存中也有，则必须要删除
	 */
	@CacheEvict(value = "mysql:role", keyGenerator = "simpleKey")
	public void delete(Long id) {
		roleRepository.delete(id);
	}
}
