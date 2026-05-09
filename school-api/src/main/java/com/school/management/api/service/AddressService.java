package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.Address;
import com.school.management.api.model.requstModel.AddressRequest;
import com.school.management.api.model.responseModel.AddressResponse;
import com.school.management.api.repository.AddressRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import com.school.management.api.service.mapper.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    MapperService mapperService;

    public AddressResponse createAddress(AddressRequest request) {

        String schoolId = request.getSchoolId() != null ? request.getSchoolId() : AuthUtil.getCurrentSchoolId();

        Address address = new Address();
        address.setAddressId(IdGenerator.generateStudentId("AD"));
        address.setName(request.getName());
        address.setCity(request.getCity());
        address.setDistrict(request.getDistrict());
        address.setPincode(request.getPincode());
        address.setPlace(request.getPlace());
        address.setState(request.getState());
        address.setSchoolId(schoolId);
        address.setLandmark(request.getLandmark());
        address.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        address.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        Address saveAddress = addressRepository.save(address);
        return mapperService.toCreateAddress(saveAddress);

    }
}
