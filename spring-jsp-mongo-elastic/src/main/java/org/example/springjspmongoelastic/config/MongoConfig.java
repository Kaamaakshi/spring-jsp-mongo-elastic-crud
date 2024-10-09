package org.example.springjspmongoelastic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.example.springjspmongoelastic.repository.mongo")
public class MongoConfig {
}
