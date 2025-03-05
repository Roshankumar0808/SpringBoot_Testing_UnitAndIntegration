package com.springBootTesting.springBootTesting.services;

import com.springBootTesting.springBootTesting.dto.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Long id,EmployeeDTO employeeDTO);
    void deleteEmployee(Long Id);
}
