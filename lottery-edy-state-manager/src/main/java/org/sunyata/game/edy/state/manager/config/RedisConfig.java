package org.sunyata.game.edy.state.manager.config;

/**
 * Created by leo on 17/11/1.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * Created by leo on 17/5/8.
 */
@Configuration
@ConditionalOnProperty(value = "octopus.state.enabled", matchIfMissing = false)
public class RedisConfig {

    Logger logger = LoggerFactory.getLogger(RedisConfig.class);


    @Value("${octopus.state.redis.host}")
    public String host;
    @Value("${octopus.state.redis.port}")
    public Integer port;
    @Value("${octopus.state.redis.password}")
    public String password;
    @Value("${octopus.state.redis.db.index}")
    public Integer dbIndex;


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        logger.info("redis.host:{},port:{}", host, port);
        //JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setDatabase(dbIndex);
        configuration.setPort(port);
        //connectionFactory.setHostName(host);
        //connectionFactory.setPort(port);
        if (!StringUtils.isEmpty(password)) {
            //connectionFactory.setPassword(password);
            configuration.setPassword(RedisPassword.of(password));
        }
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration);
        return jedisConnectionFactory;
    }

    @Bean
    @Autowired
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(jackson2JsonRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public Jackson2JsonRedisSerializer<? extends Object> jackson2JsonRedisSerializer() {
        //        final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
//                .json()
//                .build();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return new Jackson2JsonRedisSerializer<Object>(Object.class);
    }
}
