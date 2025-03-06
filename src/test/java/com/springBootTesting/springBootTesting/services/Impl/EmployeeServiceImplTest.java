package com.springBootTesting.springBootTesting.services.Impl;

import com.springBootTesting.springBootTesting.TestContainerConfig;
import com.springBootTesting.springBootTesting.dto.EmployeeDTO;
import com.springBootTesting.springBootTesting.entities.EmployeeEntities;
import com.springBootTesting.springBootTesting.exception.ResourceNotFoundException;
import com.springBootTesting.springBootTesting.repository.EmployeeRepository;
import com.springBootTesting.springBootTesting.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@DataJpaTest
@Import(TestContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;


    private  EmployeeEntities mockemployee;
    private  EmployeeDTO mockemployeeDto;

    private  EmployeeEntities mockemployee2;
    private  EmployeeDTO mockemployeeDto2;
    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @BeforeEach
    void setUp(){
         mockemployee=EmployeeEntities.builder().id(1L).email("raj@gmail.com").name("RAJ").salary(2800L).build();
        mockemployeeDto=modelMapper.map(mockemployee,EmployeeDTO.class);

        mockemployee2=EmployeeEntities.builder().id(2L).email("roshan@gmail.com").name("Roshan").salary(1800L).build();
        mockemployeeDto2=modelMapper.map(mockemployee2,EmployeeDTO.class);
    }

  @Test
  void testGetEmployeeId_when_EmployeeIdIsPresent_ThenReturnEmpoyeeDTO(){
      //ASSIGN
      Long id=mockemployee.getId();
      when(employeeRepository.findById(id)).thenReturn(Optional.of(mockemployee));

      //ACT
    //  System.out.println("Mocked Employee: " + employeeRepository.findById(id));
      EmployeeDTO employeeDTO=employeeService.getEmployeeById(id);
      //ASSERT
      assertThat(employeeDTO.getId()).isEqualTo(id);
      assertThat(employeeDTO.getEmail()).isEqualTo(mockemployee.getEmail());
      verify(employeeRepository,only()).findById(id);
      assertThat(employeeDTO).isNotNull();
      //verify(employeeRepository).save(null);
  }

  @Test
  void testGetEmployeeId_whenEmployeeIsNotPresent_thenThrowException(){
        //arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //act and assert
      assertThatThrownBy(()->employeeService.getEmployeeById(1L))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage("Employee not found with id: 1");

      verify(employeeRepository).findById(1L);
  }

    @Test
    void testDeleteByEmployeeId_whenEmployeeIsPresent(){
        //arrange
        when(employeeRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);
        //act and assert
        employeeService.deleteEmployee(1L);
        verify(employeeRepository).deleteById(anyLong());
    }
    @Test
    void testDeleteByEmployeeId_whenEmployeeIsNotPresent(){
        //arrange
        when(employeeRepository.existsById(anyLong())).thenReturn(Boolean.FALSE);
        //act and assert

        assertThatThrownBy(()->employeeService.deleteEmployee(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Employee not found with id: " + 1L);
        verify(employeeRepository,never()).deleteById(anyLong());
    }

  @Test
  void testCreateNewEmployee_whenAttemptingToCreateEmployeeWithExistingEmail_thenThrowException(){
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of(mockemployee));

        assertThatThrownBy(()->employeeService.createNewEmployee(mockemployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email:"+mockemployee.getEmail());

        verify(employeeRepository).findByEmail(mockemployeeDto.getEmail());
        verify(employeeRepository,never()).save(any());
  }

    @Test
    void testUpdateEmployee_whenAttemptingUpdateEmployeeWithExistingEmail_thenThrowException(){
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(()->employeeService.updateEmployee(mockemployee.getId(),mockemployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: " + mockemployee.getId());

//        assertThatThrownBy(()->employeeService.updateEmployee(mockemployee.getId(),mockemployeeDto))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessage("The email of the employee cannot be updated" + mockemployee.getEmail());

    }

    @Test
    void testUpdateEmployee_whenAttemptingUpdateEmployeeWithDifferentEmail_thenThrowException(){
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockemployee2));

        assertThatThrownBy(()->employeeService.updateEmployee(mockemployee2.getId(),mockemployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of the employee cannot be updated");

    }

    @Test
    void testUpdateEmployee_whenAttemptingToUpdateEmployeeWithExistingEmail_thenHappyCase(){
        when(employeeRepository.findById(mockemployeeDto.getId())).thenReturn(Optional.of(mockemployee));
        mockemployeeDto.setName("ravi");
        mockemployeeDto.setSalary(1500L);
        EmployeeEntities newEmployee=modelMapper.map(mockemployeeDto,EmployeeEntities.class);
        when(employeeRepository.save(any(EmployeeEntities.class))).thenReturn(newEmployee);

        EmployeeDTO updatedemployeeDTO=employeeService.updateEmployee(mockemployeeDto.getId(),mockemployeeDto);
        assertThat(updatedemployeeDTO).isEqualTo(mockemployeeDto);

    }

    @Test
    void testCreateNewEmployee_WhenValidEmployee_ThenCreateNewEmployee(){
      //assign
      when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
      when(employeeRepository.save(any(EmployeeEntities.class))).thenReturn(mockemployee);
      //act
        EmployeeDTO employeeDTO=employeeService.createNewEmployee(mockemployeeDto);



      //assert
      ArgumentCaptor<EmployeeEntities>employeeEntitiesArgumentCaptor=ArgumentCaptor.forClass(EmployeeEntities.class);

      assertThat(employeeDTO).isNotNull();
      assertThat(employeeDTO.getEmail()).isEqualTo(mockemployeeDto.getEmail());
      verify(employeeRepository).save(employeeEntitiesArgumentCaptor.capture());
      EmployeeEntities capturedEmployee=employeeEntitiesArgumentCaptor.getValue();
      assertThat(capturedEmployee.getEmail()).isEqualTo(mockemployee.getEmail());
    }
}