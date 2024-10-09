package org.example.springjspmongoelastic.repository.mongo;

import org.example.springjspmongoelastic.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeMongoRepository extends MongoRepository<Employee, String> {
}
