package com.springboot.student.payload;

import lombok.Data;

@Data
public class DepartmentDto {
    private Long id;
    private String name;
    private String description;
}
