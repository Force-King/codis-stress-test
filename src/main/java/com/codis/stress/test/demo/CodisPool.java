package com.codis.stress.test.demo;

import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author guifei.qin
 * @Classname CodisPoll
 * @Description TODO
 * @Date 2019-05-24 14:40
 * @Version V1.0
 */
@Configuration
public class CodisPool {


    @Value("${codis.zkAddr}")
    private String zkAddr;

    @Value("${codis.zk.proxy.dir}")
    private String zkProxyDir;

    @Value("${codis.password}")
    private String password;

    @Value("${codis.timeout}")
    private int timeout;

    @Value("${codis.maxActive}")
    private int max_active;

    @Value("${codis.maxIdle}")
    private int max_idle;

    @Value("${codis.minIdle}")
    private int min_idle;

    @Value("${codis.maxWait}")
    private long max_wait;

    @Bean
    public JedisResourcePool getPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(max_idle);
        poolConfig.setMaxTotal(max_active);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setMaxWaitMillis(max_wait);
        poolConfig.setBlockWhenExhausted(false);

        JedisResourcePool pool = RoundRobinJedisPool.create().poolConfig(poolConfig)
                .curatorClient(zkAddr, timeout).zkProxyDir(zkProxyDir).build();
        return pool;
    }
}
