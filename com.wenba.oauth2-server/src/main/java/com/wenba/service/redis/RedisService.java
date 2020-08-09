package com.wenba.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author：tongrongbing
 * @date：created in 2020/8/8 22:05
 * @description：
 */

@Component("redisService")
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     *
     * <p>Description:[判断是否存在Key]</p>
     * @param key redis的Key
     * @return boolean true:有 false:无
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     *
     * <p>Description:[添加字符串]</p>
     * @param key redis的Key
     * @param value 添加redis的value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     *
     * <p>Description:[获得字符串]</p>
     * @param key redis的Key
     * @return String 返回字符串
     */
    public String get(String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     *
     * <p>Description:[添加对象]</p>
     * @param key redis的Key
     * @param object 添加redis的value
     */
    public void addObject(String key, Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    /**
     *
     * <p>Description:[添加带生命周期的对象]</p>
     * @param key redis的Key
     * @param object 添加redis的value
     * @param seconds 失效时间
     */
    public void addObject(String key, Object object, int seconds) {
        redisTemplate.opsForValue().set(key, object);
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     *
     * <p>Description:[添加带生命周期的对象]</p>
     * @param key redis的Key
     * @param object 添加redis的value
     * @param seconds 失效时间
     */
    public void setAndExpire(String key, String value, int seconds) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     *
     * <p>Description:[获取对象]</p>
     * @param redis的Key
     * @return Object 返回对象
     */
    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     *
     * <p>Description:[存入redis的hash]</p>
     * @param key redis的Key
     * @param field 字段值
     * @param value 存入的值
     */
    public void setHash(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     *
     * <p>Description:[存入redis的hash]</p>
     * Created on 2017年6月22日
     * @param key redis的Key
     * @param field 字段值
     * @param value 存入的值
     */
    public void setHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     *
     * <p>Description:[根据key和字段值获取内容值]</p>
     * Created on 2017年6月22日
     * @param key redis的Key
     * @param field 字段值
     * @return String 返回字符串
     */
    public String getHash(String key, String field) {
        return (String)redisTemplate.opsForHash().get(key, field);
    }

    /**
     *
     * <p>Description:[根据field删除值]</p>
     * @param key redis的Key
     * @param field 字段值
     */
    public void delHashMap(String key, String field) {
        redisTemplate.boundHashOps(key).delete(field);
    }

    /**
     *
     * <p>Description:[存入hash集合]</p>
     * Created on 2017年6月22日
     * @param key redis的Key
     * @param hashmap 存入的Map集合
     */
    public void setHashMap(String key,Map<String, Object> hashmap){
        redisTemplate.opsForHash().putAll(key, hashmap);;
    }

    /**
     *
     * <p>Description:[取出hash集合]</p>
     * Created on 2017年6月22日
     * @param key redis的Key
     * @return Map<Object, Object> 返回Map集合
     */

    public Map<Object, Object> getHashMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     *
     * <p>Description:[存入redis的Set]</p>
     * @param key redis的Key
     * @param object 对象
     */
    public void setSet(String key,Object object){
        redisTemplate.opsForSet().add(key, object);
    }

    /**
     *
     * <p>Description:[获取redis的Set]</p>
     * @param key redis的Key
     * @return Set<Object> Set集合
     */
    public Set<Object> getSet(String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * <p>Discription:[查看值是否是set成员]</p>
     * @param key set的key
     * @param value set的成员
     * @return 是否是set成员
     */
    public Boolean isSetMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     *
     * <p>Description:[设置key的过期时间，endTime格式：yyyy-MM-dd hh:mm:ss]</p>
     * @param key redis的Key
     * @param endTime 结束时间
     */
    public void setExpire(String key, Date endTime) {
        long seconds = endTime.getTime() - new Date().getTime();
        redisTemplate.expire(key, (int) (seconds / 1000), TimeUnit.SECONDS);
    }

    /**
     * <p>Discription:[获取key的过期时间]</p>
     * Created on 2017/6/22
     * @param key redis的Key
     * @return 过期时间（秒）
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     *
     * <p>Description:[在redis消息队列队尾插入数据]</p>
     * Created on 2017年6月22日
     * @param key redis的Key
     * @param object 添加的对象
     */
    public void tailPush(String key,Object object){
        redisTemplate.opsForList().rightPush(key, object);
    }

    /**
     *
     * <p>Description:[在redis消息队列对头插入数据]</p>
     * @param key redis的Key
     * @param object 添加的对象
     */
    public void headPush(String key,Object object){
        redisTemplate.opsForList().leftPush(key, object);
    }

    /**
     *
     * <p>Description:[在redis消息队列队尾删除数据]</p>
     * @param key redis的Key
     * @return Object 删除的对象
     */
    public Object tailPop(String key){
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     *
     * <p>Description:[在redis消息队列队头删除数据]</p>
     * @param key redis的Key
     * @return Object 删除的对象
     */
    public Object headPop(String key){
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     *
     * <p>Description:[删除redis的值]</p>
     * @param key
     */
    public void del(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     *
     * <p>Description:[清理redis缓存]</p>
     */
    public void flushDB(){
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }
}