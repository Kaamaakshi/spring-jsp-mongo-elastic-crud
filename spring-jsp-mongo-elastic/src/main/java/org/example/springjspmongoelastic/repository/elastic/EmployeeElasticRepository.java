package org.example.springjspmongoelastic.repository.elastic;

import org.example.springjspmongoelastic.model.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EmployeeElasticRepository extends ElasticsearchRepository<Employee, String> {
}
