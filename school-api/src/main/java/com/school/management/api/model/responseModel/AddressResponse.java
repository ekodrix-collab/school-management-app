package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressResponse {

    @JsonProperty(value = "address_id")
    private String addressId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "place")
    private String place;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "district")
    private String district;

    @JsonProperty(value = "state")
    private String state;

    @JsonProperty(value = "pincode")
    private String pincode;

    @JsonProperty(value = "landmark")
    private String landmark;

}
