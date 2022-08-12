package com.springboot.student.controller;

import com.springboot.student.payload.StudentDto;
import com.springboot.student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto){
        return new ResponseEntity<>(studentService.createStudent(studentDto), HttpStatus.CREATED);
    }

    //get all students rest api
    @GetMapping
    public List<StudentDto> getAllStudents(){
        return studentService.getAllStudents();
    }

    //get student by id
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    //update student by id rest api
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto, @PathVariable(name = "id") Long id){

        StudentDto studentResponse = studentService.updateStudent(studentDto, id);
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    //delete student by id rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable(name = "id") Long id){

        studentService.deleteStudentById(id);
        return new ResponseEntity<>("Student entity deleted successfully.", HttpStatus.OK);
    }
}
