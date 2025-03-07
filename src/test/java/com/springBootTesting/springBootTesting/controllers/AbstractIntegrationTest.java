package com.springBootTesting.springBootTesting.controllers;

import com.springBootTesting.springBootTesting.TestContainerConfig;
import com.springBootTesting.springBootTesting.dto.EmployeeDTO;
import com.springBootTesting.springBootTesting.entities.EmployeeEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "100000")
@Import(TestContainerConfig.class)
public class AbstractIntegrationTest {
    @Autowired
     WebTestClient webTestClient;

  EmployeeEntities  testEmployee=EmployeeEntities.builder().id(1L).name("Roshan").email("roshan@gamil.com").salary(1500L).build();
  EmployeeDTO  testEmployeeDto=EmployeeDTO.builder().id(1L).name("Roshan").email("roshan@gamil.com").salary(1500L).build();
}
