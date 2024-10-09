package org.example.springjspmongoelastic.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "employeedetails")
@org.springframework.data.mongodb.core.mapping.Document(collection = "employeedetails")
@Data
public class Employee {
    @Id
    private String id;
    private String name;

}
