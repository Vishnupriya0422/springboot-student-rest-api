package com.springboot.student.service;


import com.springboot.student.payload.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(Long studentId, DepartmentDto departmentDto);

    List<DepartmentDto> getDepartmentsByStudentId(Long studentId);

    DepartmentDto getDepartmentById(Long studentId, Long departmentId);

    DepartmentDto updateDepartment(Long studentId, Long departmentId, DepartmentDto departmentRequest);

    void deleteDepartment(Long studentId, Long departmentId);

}
