package org.example.springjspmongoelastic.controller;

import org.example.springjspmongoelastic.model.Employee;
import org.example.springjspmongoelastic.repository.elastic.EmployeeElasticRepository;
import org.example.springjspmongoelastic.repository.mongo.EmployeeMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeMongoRepository employeeMongoRepository;
    @Autowired
    EmployeeElasticRepository employeeElasticRepository;

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("message", "Welcome to the Employee Management System!");
        return "welcome"; // The name of the JSP file (welcome.jsp)
    }

//show addemployee form

    @GetMapping("/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee()); // Initialize an empty employee object
        return "addEmployee";
    }
//save data

    @PostMapping("/employees")
    public String saveEmployee(@ModelAttribute Employee employee) {
        // Save to both MongoDB and Elasticsearch
        employeeMongoRepository.save(employee);
        employeeElasticRepository.save(employee);
        return "redirect:/success1"; // Redirect to the list of employees after saving
    }

    @GetMapping("/success1")
    public ModelAndView showSuccessPage1() {
        ModelAndView modelAndView = new ModelAndView("success");
        modelAndView.addObject("message", "Employee Added Successfully!!");
        return modelAndView;
    }

@GetMapping("/listEmployee")
public String findAllEmployees(Model model) {
    List<Employee> allEmployees = findAllEmployeesFromBothSources();
    model.addAttribute("employees", allEmployees);
    return "listEmployees";
}
    public List<Employee> findAllEmployeesFromBothSources() {
        Set<Employee> allEmployeesSet = new HashSet<>();
        allEmployeesSet.addAll(employeeMongoRepository.findAll());

        // employeeElasticRepository returns an iterable of employees
        employeeElasticRepository.findAll().forEach(allEmployeesSet::add);

        return allEmployeesSet.stream().collect(Collectors.toList());
    }


    @GetMapping("/delete/{id}")
    public ModelAndView deleteEmployee(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Employee> employeeMongo = employeeMongoRepository.findById(id);
        Optional<Employee> employeeElastic = employeeElasticRepository.findById(id);
        Employee employee = employeeMongo.orElseGet(() -> employeeElastic.orElse(null));
        if (employee != null) {
            employeeMongoRepository.delete(employee);
            employeeElasticRepository.delete(employeeElastic.orElse(null));
            modelAndView.setViewName("redirect:/success");
            return modelAndView;
        }else{
//
            modelAndView.setViewName("redirect:/error");
            return modelAndView;
        }
    }

    @GetMapping("/success")
    public ModelAndView showSuccessPage() {
        ModelAndView modelAndView = new ModelAndView("success");
        modelAndView.addObject("message", "Employee Deleted Successfully!!");
        return modelAndView;
    }

    @GetMapping("/deleteAll")
    public String deleteAllEmployees() {

        employeeMongoRepository.deleteAll();
        employeeElasticRepository.deleteAll();

        return "redirect:/add";
    }

    @GetMapping("/update")
    public ModelAndView updateEmployeeForm(@RequestParam String id, Model model) {
        ModelAndView modelAndView = new ModelAndView("updateEmployee");
        Optional<Employee> employeeMongo = employeeMongoRepository.findById(id);
        Optional<Employee> employeeElastic = employeeElasticRepository.findById(id);
        Employee employee = employeeMongo.orElseGet(() -> employeeElastic.orElse(null));
        if (employee != null) {
            model.addAttribute("employee", employee);
            return modelAndView;
        }else{
            modelAndView.addObject("id",id);
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    //update employee
    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute Employee employee, Model model) {
        employeeMongoRepository.save(employee);
        employeeElasticRepository.save(employee);
        model.addAttribute("message", "Updated Successfully!!");
        model.addAttribute("employee", employee);
        return "updateEmployee";
    }
}



