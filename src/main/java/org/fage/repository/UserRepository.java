package org.fage.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.fage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/**
 * 
 * @author Caizhfy
 * @email caizhfy@163.com
 * @createTime 2017年10月31日
 * @description 继承repository接口，获得支持
 * 				原理：单顶层借口+抽象类+本接口+代理本接口的实现类
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	//And用法
	User findById(long id);
	
	User findByIdAndUsername(long id, String username);
	
	//Or用法
	User findByIdOrUsername(long id, String name);
	
	//Between用法
	User findByCreateDateBetween(Date start, Date end);
	
	//LessThan用法
	List<User> findByCreateDateLessThan(Date start);
	
	//GreaterThan用法
	List<User> findByCreateDateGreaterThan(Date start);
	
	//IsNull/IsNutNull用法
	List<User> findByUsernameIsNull();
	
	//Like/NotLike用法
	List<User> findByUsernameLike(String username);
	
	//OrderBy用法
	List<User> findByUsernameOrderByIdAsc(String username);
	
	//Not用法
	List<User> findByUsernameNot(String username);
	
	//In/NotIn用法
	List<User> findByUsernameIn(Collection<String> nameList);
	
	
}
