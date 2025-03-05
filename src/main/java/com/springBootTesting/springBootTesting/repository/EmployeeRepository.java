package com.springBootTesting.springBootTesting.repository;

import com.springBootTesting.springBootTesting.entities.EmployeeEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface EmployeeRepository extends JpaRepository<EmployeeEntities,Long> {
    List<EmployeeEntities>findByEmail(String email);
}
