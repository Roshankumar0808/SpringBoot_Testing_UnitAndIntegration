package com.springBootTesting.springBootTesting.services.Impl;

import com.springBootTesting.springBootTesting.TestContainerConfig;
import com.springBootTesting.springBootTesting.dto.EmployeeDTO;
import com.springBootTesting.springBootTesting.entities.EmployeeEntities;
import com.springBootTesting.springBootTesting.repository.EmployeeRepository;
import com.springBootTesting.springBootTesting.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@DataJpaTest
@Import(TestContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

  @Test
  void testGetEmployeeId_when_EmployeeIdIsPresent_ThenReturnEmpoyeeDTO(){
      //ASSIGN
      Long id=1L;
      EmployeeEntities mockemployee=EmployeeEntities.builder().id(id).email("raj@gmail.com").name("RAJ").salary(2800L).build();
      when(employeeRepository.findById(id)).thenReturn(Optional.of(mockemployee));

      //ACT
    //  System.out.println("Mocked Employee: " + employeeRepository.findById(id));
      EmployeeDTO employeeDTO=employeeService.getEmployeeById(id);
      //ASSERT
      assertThat(employeeDTO.getId()).isEqualTo(id);
      assertThat(employeeDTO.getEmail()).isEqualTo(mockemployee.getEmail());
      verify(employeeRepository).findById(id);
      //verify(employeeRepository).save(null);
  }
}