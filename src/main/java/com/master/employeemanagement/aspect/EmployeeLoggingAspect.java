package com.master.employeemanagement.aspect;

import com.master.employeemanagement.model.Employee;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeLoggingAspect.class);

    @AfterReturning("execution(* com.master.employeemanagement.controller.DepartementController.addEmployeeToDepartment(..)) && args(employee)")
    public void logEmployeeAddition(Employee employee) {
        logger.info("Employee added: {}", employee);
    }

    @AfterReturning("execution(* com.master.employeemanagement.controller.EmployeeController.updateEmployee(..)) && args(id, employeeDetails)")
    public void logEmployeeUpdate(Long id, Employee employeeDetails) {
        logger.info("Employee with ID {} updated: {}", id, employeeDetails);
    }

    @AfterReturning("execution(* com.master.employeemanagement.controller.EmployeeController.deleteEmployeeById(..)) && args(id)")
    public void logEmployeeDeletion(Long id) {
        logger.info("Employee with ID {} deleted", id);
    }
}