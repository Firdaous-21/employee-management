package com.master.employeemanagement.controller;

import com.master.employeemanagement.model.Employee;
import com.master.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/departement/{id}/employes")
    public List<Employee> getEmployesByDepartement(@PathVariable Long id) {
        return employeeService.getEmployeeByDepartement(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long id) {
        try {
            String path = "Users/LEGION/IdeaProjects/employee-management/src/main/resources/static/pictures" + id + ".png";
            File file = new File(path);
            if(file.exists()) {
                file.delete();
            }
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok(true);
        }catch (Exception ignored) {
            return ResponseEntity.ok(false);
        }
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee( @PathVariable Long id,
                                   @RequestParam String nom,
                                   @RequestParam String email,
                                   @RequestParam int age,
                                   @RequestParam MultipartFile file) throws IOException {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if(employee.isPresent()) {
            employee.get().setNom(nom);
            employee.get().setEmail(email);
            employee.get().setAge(age);

            String path = "Users/LEGION/IdeaProjects/employee-management/src/main/resources/static/pictures" + id + ".png";
            file.transferTo(Path.of(path));
            String photoUrl = "http://localhost:8080/api/photos/" + id;
            employee.get().setPhoto(photoUrl);
            return ResponseEntity.ok(employeeService.updateEmployee(employee.get()));
        }
        return null;
    }

    @DeleteMapping("/age/{age}")
    public ResponseEntity<Void> deleteEmployeesOverAge(@PathVariable int age) {
        employeeService.deleteEmployeesOverAge(age);
        return ResponseEntity.noContent().build();
    }
}
