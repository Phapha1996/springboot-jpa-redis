package org.fage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * @author Caizhfy
 * @email caizhfy@163.com
 * @createTime 2017年11月1日
 * @description redis配置类
 * 				打开redis：src/redis-server /etc/redis.conf
 *
 */
@Configuration
@EnableCaching
public class RedisConfiguration {

	//注入redis链接工厂,为json转化做配置
	@Bean
	public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory factory){
		StringRedisTemplate template = new StringRedisTemplate(factory);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

	/**
	 * 如果使用cache注解，只能存储简单对象（无包含的对象实体）
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedisTemplate redisTemplate){
		RedisCacheManager manager = new RedisCacheManager(redisTemplate);
		manager.setDefaultExpiration(60*1000);	//设置缓存时间为60秒
		return manager;
	}
	
	/*
	 * 缓存简单键生成策略（用id+类信息生成策略）
	 */
	 @Bean
	    public KeyGenerator simpleKey(){
	        return new KeyGenerator() {
	            @Override
	            public Object generate(Object target, Method method, Object... params) {
	                StringBuilder sb = new StringBuilder();
	                sb.append(target.getClass().getName()+":");
	                for (Object obj : params) {
	                    sb.append(obj.toString());
	                }
	                return sb.toString();
	            }
	        };
	    }

	 /*
	  * 缓存复杂键（对象键）生成策略（用实体的id+类信息做键）
	  */
	    @Bean
	    public KeyGenerator objectId(){
	        return new KeyGenerator() {
	            @Override
	            public Object generate(Object target, Method method, Object... params){
	                StringBuilder sb = new StringBuilder();
	                sb.append(target.getClass().getName()+":");
	                try {
	                    sb.append(params[0].getClass().getMethod("getId", null).invoke(params[0], null).toString());
	                }catch (NoSuchMethodException no){
	                    no.printStackTrace();
	                }catch(IllegalAccessException il){
	                    il.printStackTrace();
	                }catch(InvocationTargetException iv){
	                    iv.printStackTrace();
	                }
	                return sb.toString();
	            }
	        };
	    }
}
