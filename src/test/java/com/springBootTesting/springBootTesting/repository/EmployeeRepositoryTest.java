package com.springBootTesting.springBootTesting.repository;

import com.springBootTesting.springBootTesting.TestContainerConfig;
import com.springBootTesting.springBootTesting.dto.EmployeeDTO;
import com.springBootTesting.springBootTesting.entities.EmployeeEntities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Import(TestContainerConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeEntities employeeEntities;


    @BeforeEach
    void setUp(){
        employeeEntities=EmployeeEntities.builder()
                .Id(1L)
                .email("ravi@gmail.com")
                .salarly(100L)
                .build();
    }
    @Test
    void testFindByEmail_whenEmailIsValid_thenReturnEmployee() {
       //Arrange,Given
         employeeRepository.save(employeeEntities);
        //Act,When


        List<EmployeeEntities> employeeEntitiesList=employeeRepository.findByEmail(employeeEntities.getEmail());
        //Assert,Then
        assertThat(employeeEntitiesList).isNotNull();
        assertThat(employeeEntitiesList).isNotEmpty();
        assertThat(employeeEntitiesList.get(0).getEmail()).isEqualTo(employeeEntities.getEmail());
    }

    @Test
    void testFindByEmail_whenEmailIs_thenNotFound_ReturnEmptyEmployeeList() {
     String email="ravi123@gmail.com";
     List<EmployeeEntities>employeeEntitiesList=employeeRepository.findByEmail(email);
     assertThat(employeeEntitiesList).isNotNull();
     assertThat(employeeEntitiesList).isEmpty();

    }
}