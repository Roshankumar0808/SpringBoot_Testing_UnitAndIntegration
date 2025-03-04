package com.springBootTesting.springBootTesting.dto;

import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String name;
    private  String email;
    private Long salarly;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getSalarly(), that.getSalarly());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getSalarly());
    }
}
