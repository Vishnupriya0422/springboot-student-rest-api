package com.springboot.student.controller;

import com.springboot.student.payload.AddressDto;
import com.springboot.student.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class AddressController {
    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/students/{studentId}/addresses")
    public ResponseEntity<AddressDto> createAddress(@PathVariable(value = "studentId") Long studentId,
                                                    @RequestBody AddressDto addressDto){
        return new ResponseEntity<>(addressService.createAddress(studentId, addressDto), HttpStatus.CREATED);
    }

    @GetMapping("/students/{studentId}/addresses")
    public List<AddressDto> getAddressesByStudentId(@PathVariable(value = "studentId") Long studentId){
        return addressService.getAddressesByStudentId(studentId);
    }

    @GetMapping("/students/{studentId}/addresses/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable(value = "studentId") Long studentId,
                                                     @PathVariable(value = "id") Long addressId){
        AddressDto addressDto = addressService.getAddressById(studentId, addressId);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @PutMapping("/students/{studentId}/addresses/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable(value = "studentId") Long studentId,
                                                    @PathVariable(value = "id") Long addressId,
                                                    @RequestBody AddressDto addressDto){
        AddressDto updatedAddress = addressService.updateAddress(studentId, addressId, addressDto);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/students/{studentId}/addresses/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable(value = "studentId") Long studentId,
                                                @PathVariable(value = "id") Long addressId){
        addressService.deleteAddress(studentId, addressId);
        return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
    }
}
