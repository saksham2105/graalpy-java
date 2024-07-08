package com.exxeta.projectmatcher.controller;

import com.exxeta.projectmatcher.model.Employee;
import com.exxeta.projectmatcher.model.Project;
import com.exxeta.projectmatcher.service.EmployeeService;
import com.exxeta.projectmatcher.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class EmployeeController {
    EmployeeService employeeService;
    RecommendationService recommendationService;

    public EmployeeController(
        EmployeeService employeeService,
        RecommendationService recommendationService
    ) {
        this.employeeService = employeeService;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/employee")
    Iterable<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/employee/{id}")
    Optional<Employee> findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping("/employee")
    Employee save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @DeleteMapping("/employee/{id}")
    void deleteById(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

    @PostMapping("/employee/recommendation")
    List<Employee> getRecommendation(@RequestBody Project project) {
        log.info("Chala");
        Iterable<Employee> employeeIterable = employeeService.findAll();
        log.info("Iterable : " + employeeIterable.iterator().next());
        return recommendationService.recommendEmployees(
                project,
                employeeService.findAll()
        );
    }
}
