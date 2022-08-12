package com.springboot.student.repository;

import com.springboot.student.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByStudentId(Long studentId);

}
