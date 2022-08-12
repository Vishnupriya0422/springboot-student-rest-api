package com.springboot.student.service.impl;

import com.springboot.student.entity.Student;
import com.springboot.student.exception.ResourceNotFoundException;
import com.springboot.student.payload.StudentDto;
import com.springboot.student.repository.StudentRepository;
import com.springboot.student.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private ModelMapper mapper;


    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper mapper) {

        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {

        // convert DTO to entity
        Student student = mapToEntity(studentDto);
        Student newStudent = studentRepository.save(student);

        //convert entity to DTO
        StudentDto studentResponse = mapToDTO(newStudent);
        return studentResponse;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> mapToDTO(student)).collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        return mapToDTO(student);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto, Long id) {

        //get student by id from the database
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        student.setName(studentDto.getName());
        student.setDob(studentDto.getDob());
        student.setDoj(studentDto.getDoj());

        Student updatedStudent = studentRepository.save(student);
        return mapToDTO(updatedStudent);
    }

    @Override
    public void deleteStudentById(Long id) {
        //delete student by id from the database
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        studentRepository.delete(student);
    }

    //convert Entity into DTO
    private StudentDto mapToDTO(Student student){

        StudentDto studentDto = mapper.map(student, StudentDto.class);
        //StudentDto studentDto = new StudentDto();
        //studentDto.setId(student.getId());
        //studentDto.setName(student.getName());
        //studentDto.setDob(student.getDob());
        //studentDto.setDoj(student.getDoj());
        return studentDto;
    }

    //convert DTO into Entity

    private Student mapToEntity(StudentDto studentDto){


        Student student = mapper.map(studentDto, Student.class);
//        Student student = new Student();
//        student.setName(studentDto.getName());
//        student.setDob(studentDto.getDob());
//        student.setDoj(studentDto.getDoj());
        return student;
    }
}
