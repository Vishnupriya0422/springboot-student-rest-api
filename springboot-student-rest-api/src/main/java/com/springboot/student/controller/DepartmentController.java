package com.springboot.student.controller;

import com.springboot.student.payload.DepartmentDto;
import com.springboot.student.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/students/{studentId}/departments")
    public ResponseEntity<DepartmentDto> createDepartment(@PathVariable(value = "studentId") Long studentId,
                                                          @RequestBody DepartmentDto departmentDto){

        return new ResponseEntity<>(departmentService.createDepartment(studentId, departmentDto), HttpStatus.CREATED);
    }

    @GetMapping("/students/{studentId}/departments")
    public List<DepartmentDto> getDepartmentsByStudentId(@PathVariable(value = "studentId") Long studentId){
        return departmentService.getDepartmentsByStudentId(studentId);
    }

    @GetMapping("/students/{studentId}/departments/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(value = "studentId") Long studentId,
                                                     @PathVariable(value = "id") Long departmentId){
        DepartmentDto departmentDto = departmentService.getDepartmentById(studentId, departmentId);
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    @PutMapping("/students/{studentId}/departments/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable(value = "studentId") Long studentId,
                                                    @PathVariable(value = "id") Long departmentId,
                                                    @RequestBody DepartmentDto departmentDto){
        DepartmentDto updatedDepartment = departmentService.updateDepartment(studentId, departmentId, departmentDto);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    @DeleteMapping("/students/{studentId}/departments/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable(value = "studentId") Long studentId,
                                                @PathVariable(value = "id") Long departmentId){
        departmentService.deleteDepartment(studentId, departmentId);
        return new ResponseEntity<>("Department deleted successfully", HttpStatus.OK);
    }

}
