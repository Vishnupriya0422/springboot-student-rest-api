package com.springboot.student.service.impl;

import com.springboot.student.entity.Department;
import com.springboot.student.entity.Student;
import com.springboot.student.exception.ResourceNotFoundException;
import com.springboot.student.exception.StudentdetailsAPIException;
import com.springboot.student.payload.DepartmentDto;
import com.springboot.student.repository.DepartmentRepository;
import com.springboot.student.repository.StudentRepository;
import com.springboot.student.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private StudentRepository studentRepository;

    private ModelMapper mapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, StudentRepository studentRepository, ModelMapper mapper) {
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public DepartmentDto createDepartment(Long studentId, DepartmentDto departmentDto) {

        Department department = mapToEntity(departmentDto);

        //retrieve student entity by id
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", studentId));

        //set student to department entity
        department.setStudent(student);

        //dept entity to DB
        Department newDepartment = departmentRepository.save(department);

        return mapToDTO(newDepartment);

    }

    @Override
    public List<DepartmentDto> getDepartmentsByStudentId(Long studentId) {
        //retrieve departments by studentId
        List<Department> departments = departmentRepository.findByStudentId(studentId);

        return departments.stream().map(department -> mapToDTO(department)).collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getDepartmentById(Long studentId, Long departmentId) {

        //retrieve student entity by id
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", studentId));

        //retrieve department entity by id
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department", "id", departmentId));

        if(!department.getStudent().getId().equals(student.getId())){
            throw new StudentdetailsAPIException(HttpStatus.BAD_REQUEST, "Department does not belong to student");
        }

        return mapToDTO(department);

    }

    @Override
    public DepartmentDto updateDepartment(Long studentId, Long departmentId, DepartmentDto departmentRequest) {
        //retrieve student entity by id
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", studentId));

        //retrieve department entity by id
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department", "id", departmentId));

        if(!department.getStudent().getId().equals(student.getId())){
            throw new StudentdetailsAPIException(HttpStatus.BAD_REQUEST, "Department does not belong to student");
        }

        department.setName(departmentRequest.getName());
        department.setDescription(departmentRequest.getDescription());

        Department updatedDepartment = departmentRepository.save(department);
        return mapToDTO(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long studentId, Long departmentId) {

        //retrieve student entity by id
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", studentId));

        //retrieve dept entity by id
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department", "id", departmentId));

        if(!department.getStudent().getId().equals(student.getId())){
            throw new StudentdetailsAPIException(HttpStatus.BAD_REQUEST, "Department does not belong to student");
        }

        departmentRepository.delete(department);

    }

    private DepartmentDto mapToDTO(Department department){

        DepartmentDto departmentDto = mapper.map(department, DepartmentDto.class);
//        DepartmentDto departmentDto = new DepartmentDto();
//        departmentDto.setId(department.getId());
//        departmentDto.setName(department.getName());
//        departmentDto.setDescription(department.getDescription());
        return departmentDto;
    }

    private Department mapToEntity(DepartmentDto departmentDto){

        Department department = mapper.map(departmentDto, Department.class);
//        Department department = new Department();
//        department.setName(departmentDto.getName());
//        department.setDescription(departmentDto.getDescription());
        return department;
    }
}
