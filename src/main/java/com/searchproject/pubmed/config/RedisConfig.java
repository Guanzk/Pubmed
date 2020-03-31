package com.searchproject.pubmed.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@Slf4j
public class RedisConfig {

    @Value("${spring.application.name:unknown}")
    private String appName;
    @Value("${spring.redis.timeToLive:15}")
    private Long timeToLive;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
    @Scope(value = "prototype")
    public GenericObjectPoolConfig redisPool() {
        return new GenericObjectPoolConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.redis-a")
    public RedisStandaloneConfiguration redisConfigA() {

        return new RedisStandaloneConfiguration();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.redis-b")
    public RedisStandaloneConfiguration redisConfigB() {
        return new RedisStandaloneConfiguration();
    }



    @Bean("factoryA")
    @Primary
    public LettuceConnectionFactory factoryA(GenericObjectPoolConfig config, RedisStandaloneConfiguration redisConfigA) {

        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(config).build();
        return new LettuceConnectionFactory(redisConfigA, clientConfiguration);
    }

    @Bean("factoryB")
    public LettuceConnectionFactory factoryB(GenericObjectPoolConfig config, RedisStandaloneConfiguration redisConfigB) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(config).build();
        return new LettuceConnectionFactory(redisConfigB, clientConfiguration);
    }


    @Bean(name = "redisTemplateA")
    @Primary
    public StringRedisTemplate redisTemplateA(@Qualifier("factoryA") LettuceConnectionFactory factoryA) {
        factoryA.setShareNativeConnection(false);
        StringRedisTemplate template = new StringRedisTemplate(factoryA);

        return template;
    }

    @Bean(name = "redisTemplateB")
    public StringRedisTemplate redisTemplateB( @Qualifier("factoryB") LettuceConnectionFactory factoryB) {
        StringRedisTemplate template = new StringRedisTemplate(factoryB);

        return template;
    }



}
