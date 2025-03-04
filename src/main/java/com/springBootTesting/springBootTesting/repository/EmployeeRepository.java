package com.springBootTesting.springBootTesting.repository;

import com.springBootTesting.springBootTesting.entities.EmployeeEntities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntities,Long> {
    List<EmployeeEntities>findByEmail(String email);
}
