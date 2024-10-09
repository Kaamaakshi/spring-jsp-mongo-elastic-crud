package org.example.springjspmongoelastic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
@Configuration
@EnableElasticsearchRepositories(basePackages = "org.example.springjspmongoelastic.repository.elastic")
public class ElasticConfig {
}
