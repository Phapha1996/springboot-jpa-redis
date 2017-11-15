package org.fage.service;

import org.fage.domain.Department;
import org.fage.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;

	/*
	 * 存取缓存，如果缓存没有就找数据库，如果数据库有就拿出来存入缓存
	 */
	@Cacheable(value = "mysql:department", keyGenerator = "simpleKey")
	public Department findById(Long id) {
		return departmentRepository.findOne(id);
	}

	/*
	 * 修改缓存，如果有就修改缓存，如果没有就存入缓存
	 */
	@CachePut(value = "mysql:department", keyGenerator = "objectId")
	public Department create(Department deparment) {
		return departmentRepository.save(deparment);
	}

	/*
	 * 与上述一样
	 */
	@CachePut(value = "mysql:department", keyGenerator = "objectId")
	public Department update(Department role) {
		return departmentRepository.save(role);
	}

	/*
	 * 删除缓存，从数据库中删除数据，如果缓存中也有，则必须要删除
	 */
	@CacheEvict(value = "mysql:deparment", keyGenerator = "simpleKey")
	public void delete(Long id) {
		departmentRepository.delete(id);
	}
}
