package org.fage.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.fage.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 
 * @author Caizhfy
 * @email caizhfy@163.com
 * @createTime 2017年11月1日
 * @description redis持久化类
 *
 */
@Repository
public class UserRedis {
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	public void add(String key, Long time, User user){
		//以分钟为单位，超过该分钟数redis自动从数据库中清除该对象
		redisTemplate.opsForValue().set(key, new Gson().toJson(user), time, TimeUnit.MINUTES);
	}
	
	public void add(String key, Long time, List<User> users){
		redisTemplate.opsForValue().set(key, new Gson().toJson(users), time, TimeUnit.MINUTES);
	}
	
	public User get(String key){
		User user = null;
		String userJson = redisTemplate.opsForValue().get(key);
		if(!StringUtils.isEmpty(userJson)){
			user = new Gson().fromJson(userJson, User.class);
		}
		return user;
	}
	
	public List<User> getList(String key){
		List<User> list = null;
		String userListStr = redisTemplate.opsForValue().get(key);
		if(!StringUtils.isEmpty(userListStr)){
			list = new Gson().fromJson(userListStr, new TypeToken<List<User>>(){}.getType());
		}
		return list;
	}
	
	public void delete(String key){
		redisTemplate.opsForValue().getOperations().delete(key);
	}
	
}
