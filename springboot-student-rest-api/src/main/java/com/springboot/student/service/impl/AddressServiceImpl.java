package com.springboot.student.service.impl;

import com.springboot.student.entity.Address;
import com.springboot.student.entity.Student;
import com.springboot.student.exception.ResourceNotFoundException;
import com.springboot.student.exception.StudentdetailsAPIException;
import com.springboot.student.payload.AddressDto;
import com.springboot.student.repository.AddressRepository;
import com.springboot.student.repository.StudentRepository;
import com.springboot.student.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;
    private StudentRepository studentRepository;

    private ModelMapper mapper;


    public AddressServiceImpl(AddressRepository addressRepository, StudentRepository studentRepository, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public AddressDto createAddress(Long studentId, AddressDto addressDto) {

        Address address = mapToEntity(addressDto);

        //retrieve student entity by id
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", studentId));

        //set student to address entity
        address.setStudent(student);

        //address entity to DB
        Address newAddress = addressRepository.save(address);

        return mapToDTO(newAddress);
    }

    @Override
    public List<AddressDto> getAddressesByStudentId(Long studentId) {
        //retrieve addresses by studentId
        List<Address> addresses = addressRepository.findByStudentId(studentId);

        return addresses.stream().map(address -> mapToDTO(address)).collect(Collectors.toList());
    }

    @Override
    public AddressDto getAddressById(Long studentId, Long addressId) {

        //retrieve student entity by id
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", studentId));

        //retrieve address entity by id
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new ResourceNotFoundException("Address", "id", addressId));

        if(!address.getStudent().getId().equals(student.getId())){
            throw new StudentdetailsAPIException(HttpStatus.BAD_REQUEST, "Address does not belong to student");
        }

        return mapToDTO(address);
    }

    @Override
    public AddressDto updateAddress(Long studentId, Long addressId, AddressDto addressRequest) {

        //retrieve student entity by id
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", studentId));

        //retrieve address entity by id
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new ResourceNotFoundException("Address", "id", addressId));

        if(!address.getStudent().getId().equals(student.getId())){
            throw new StudentdetailsAPIException(HttpStatus.BAD_REQUEST, "Address does not belong to student");
        }

        address.setAddress(addressRequest.getAddress());

        Address updatedAddress = addressRepository.save(address);
        return mapToDTO(updatedAddress);
    }

    @Override
    public void deleteAddress(Long studentId, Long addressId) {

        //retrieve student entity by id
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", studentId));

        //retrieve address entity by id
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new ResourceNotFoundException("Address", "id", addressId));

        if(!address.getStudent().getId().equals(student.getId())){
            throw new StudentdetailsAPIException(HttpStatus.BAD_REQUEST, "Address does not belong to student");
        }

        addressRepository.delete(address);
    }

    private AddressDto mapToDTO(Address address){

        AddressDto addressDto = mapper.map(address, AddressDto.class);
//        AddressDto addressDto = new AddressDto();
//        addressDto.setId(address.getId());
//        addressDto.setAddress(address.getAddress());
        return addressDto;
    }

    private Address mapToEntity(AddressDto addressDto){

        Address address = mapper.map(addressDto, Address.class);
//        Address address = new Address();
//        address.setId(addressDto.getId());
//        address.setAddress(addressDto.getAddress());
        return address;
    }
}
