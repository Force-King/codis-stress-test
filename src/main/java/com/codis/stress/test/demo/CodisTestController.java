package com.codis.stress.test.demo;

import com.alibaba.fastjson.JSONObject;
import io.codis.jodis.JedisResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author guifei.qin
 * @Classname CodisTestController
 * @Description Codis 压力测试入口
 * @Date 2019-05-24 14:43
 * @Version V1.0
 */
@RestController
public class CodisTestController {
    private static final Logger logger = LoggerFactory.getLogger(CodisTestController.class);

    @Autowired
    private JedisResourcePool jedisPool;

//    @Autowired
//    private JedisPool jedisPool;

    @RequestMapping(path = {"/codis/test/get"})
    public String get(String key) {
        logger.info("codis get by key:{}",key);
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("codis exception, get failed! key ={} ",key,e);
            return null;
        }
    }

    @RequestMapping(path = {"/codis/test/set"})
    public void set(String key, String value) {
        logger.info("codis set, key:{},value:{}",key,value);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
            jedis.expire(key, 20);
        } catch (Exception e) {
            logger.error("codis exception, set failed! key = {}, value = {}.", key, value,e);
        }
    }

    @RequestMapping(path = {"/codis/abTest/get"})
    public String get() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        long id = threadLocalRandom.nextInt(100000,999999);
        String key = "uid_"+ id;
        try (Jedis jedis = jedisPool.getResource()) {
            String value = jedis.get(key);
            logger.info("codis get by key:{}, value:{}",key,value);
            return value;
        } catch (Exception e) {
            logger.error("codis exception, get failed! key ={} ",key,e);
            return null;
        }
    }

    @RequestMapping(path = {"/codis/abTest/set"})
    public void set() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        long id = threadLocalRandom.nextInt(100000,999999);
        String key = "uid_"+ id;
        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("name","张三");
        json.put("sex","男");
        json.put("age","20");
        json.put("phone","15066668888");
        json.put("idcard","130125199501243978");
        json.put("desc","Codis压力测试");
        String value = json.toJSONString();
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
            jedis.expire(key, 60*30);
            logger.info("codis set, key:{},value:{}",key,value);
        } catch (Exception e) {
            logger.error("codis exception, set failed! key = {}, value = {}.", key, value,e);
        }
    }


}
