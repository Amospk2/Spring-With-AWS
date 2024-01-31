package com.desafioanotaai.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Bean
    public MongoDatabaseFactory mongoFactory()
    {
        return new SimpleMongoClientDatabaseFactory("mongodb://root:example@172.17.0.1:27017/springdb?authSource=admin");
    }
    @Bean
    public MongoTemplate mongoTemplate()
    {
        return new MongoTemplate(mongoFactory());
    }

}
