package com.springBootTesting.springBootTesting.config;

import com.springBootTesting.springBootTesting.repository.EmployeeRepository;
import com.springBootTesting.springBootTesting.services.Impl.EmployeeServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
