package com.master.employeemanagement.controller;

import com.master.employeemanagement.model.Departement;
import com.master.employeemanagement.model.Employee;
import com.master.employeemanagement.service.DepartementService;
import com.master.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/departements")
public class DepartementController {
    private DepartementService departementService;
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Departement>> getAllDepartements() {
        List<Departement> departements = departementService.getAllDepartements();
        return ResponseEntity.ok(departements);
    }

    @PostMapping
    public ResponseEntity<Departement> addDepartement(@RequestParam String departement) {
        Departement savedDepartement = departementService.addDepartement(departement);
        return ResponseEntity.ok(savedDepartement);
    }

    @PostMapping("/{id}/employees")
    public ResponseEntity<Employee> addEmployeeToDepartment(@PathVariable Long id,
                                            @RequestParam String nom,
                                            @RequestParam String email,
                                            @RequestParam int age,
                                            @RequestParam MultipartFile file ) throws IOException {
        Employee employee = Employee.builder().nom(nom).email(email).age(age).build();
        employee.setDepartement(departementService.getDepartementById(id).get());
        employee = employeeService.addEmployee(employee);

        String path = "Users/LEGION/IdeaProjects/employee-management/src/main/resources/static/pictures" + employee.getId() + ".png";
        file.transferTo(Path.of(path));
        String photoUrl = "http://localhost:8080/api/photos/" + employee.getId();
        employee.setPhoto(photoUrl);

        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable String id){
        String path="/Users/LEGION/IdeaProjects/employee-management/src/main/resources/static/pictures/"+id+".png";
        FileSystemResource file=new FileSystemResource(path);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(file);
    }
}
