package com.springBootTesting.springBootTesting.controllers;

import com.springBootTesting.springBootTesting.TestContainerConfig;
import com.springBootTesting.springBootTesting.dto.EmployeeDTO;
import com.springBootTesting.springBootTesting.entities.EmployeeEntities;
import com.springBootTesting.springBootTesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllersIntegrationTest extends AbstractIntegrationTest {


  @Autowired
  private EmployeeRepository employeeRepository;



  @BeforeEach
  void setup(){

      employeeRepository.deleteAll();
  }

  @Test
  void testGetEmployeeId_success(){
     EmployeeEntities employeeEntities=employeeRepository.save(testEmployee);
      webTestClient.get()
              .uri("/employees/{id}",employeeEntities.getId())
              .exchange()
              .expectBody()
              .jsonPath("$.id").isEqualTo(employeeEntities.getId())
             .jsonPath("$.email").isEqualTo(employeeEntities.getEmail());

//              .value(employeeDTO -> {
//                  assertThat(employeeDTO.getId()).isEqualTo(testEmployeeDto.getId());
//                  assertThat(employeeDTO.getEmail()).isEqualTo(testEmployeeDto.getEmail());
//              });
             // .isEqualTo(testEmployeeDto);

  }

  @Test
    void testGetEmployeeById_Failure(){
      webTestClient.get().uri("/employees/1").exchange().expectStatus().isNotFound();
  }

  @Test
    void testCreateNewEmployee_whenEmployeeAlreadyExists_then_throwExc(){
      EmployeeEntities employeeEntities=employeeRepository.save(testEmployee);
     webTestClient.post()
             .uri("/employees")
             .bodyValue(testEmployeeDto)
             .exchange()
             .expectStatus().is5xxServerError();
  }


    @Test
    void testCreateNewEmployee_whenNewEmployee_then_happycase(){
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());

    }

    @Test
    void testUpdateEmployee_whenEmployeeDoesNotFound_thenthrow(){
        webTestClient.put()
                .uri("/employees/999")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isNotFound();


    }

    @Test
    void testUpdateEmployee_whenEmployeeUpdateEmail_thenthrow(){
      EmployeeEntities employeeEntities=employeeRepository.save(testEmployee);
      testEmployeeDto.setName("raj");
      testEmployeeDto.setEmail("raj@gmail.com");
        webTestClient.put()
                .uri("/employees/{id}",employeeEntities.getId())
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }




    @Test
    void testDeleteEmployee_whenEmployeeIncorrectEmployee_throwExc(){
       webTestClient.delete()
               .uri("/employees/1")
               .exchange()
               .expectStatus().isNotFound();

    }

    @Test
    void testDeleteEmployee_whenEmployeecorrectEmployee_HappyCase(){
      EmployeeEntities employeeEntities=employeeRepository.save(testEmployee);

        webTestClient.delete()
                .uri("/employees/{id}",employeeEntities.getId())
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);
        webTestClient.delete()
                .uri("/employees/{id}",employeeEntities.getId())
                .exchange()
                .expectStatus().isNotFound();

    }



}