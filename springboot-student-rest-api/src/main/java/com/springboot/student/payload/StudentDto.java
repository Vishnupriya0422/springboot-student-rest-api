package com.springboot.student.payload;

import lombok.Data;

import java.util.Set;


@Data
public class StudentDto {
    private Long id;
    private String name;
    private String dob;
    private String doj;
    private Set<AddressDto> addresses;
    private Set<DepartmentDto> departments;
}
