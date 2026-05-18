package com.school.management.api.controller;

import com.school.management.api.model.requstModel.AddressRequest;
import com.school.management.api.model.responseModel.AddressResponse;
import com.school.management.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/create")
    public AddressResponse createAddress(@RequestBody AddressRequest request){
        return addressService.createAddress(request);
    }

    @GetMapping("/all")
    public List<AddressResponse> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{addressId}")
    public AddressResponse getAddressById(@PathVariable String addressId) {
        return addressService.getAddressById(addressId);
    }

    @DeleteMapping("/{addressId}")
    public String deleteAddress(@PathVariable String addressId) {
        return addressService.deleteAddress(addressId);
    }
}
