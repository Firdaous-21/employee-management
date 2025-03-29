package com.master.employeemanagement.repository;

import com.master.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public List<Employee> findByDepartementId(Long id);
    void deleteByAgeGreaterThan(int age);
}
