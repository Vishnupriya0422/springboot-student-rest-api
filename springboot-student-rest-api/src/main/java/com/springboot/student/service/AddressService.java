package com.springboot.student.service;

import com.springboot.student.payload.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto createAddress(Long studentId, AddressDto addressDto);

    List<AddressDto> getAddressesByStudentId(Long studentId);

    AddressDto getAddressById(Long studentId, Long addressId);

    AddressDto updateAddress(Long studentId, Long addressId, AddressDto addressRequest);

    void deleteAddress(Long studentId, Long addressId);
}
