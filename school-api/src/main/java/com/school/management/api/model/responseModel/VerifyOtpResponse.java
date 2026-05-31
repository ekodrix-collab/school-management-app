package com.school.management.api.model.responseModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyOtpResponse {
    private String message;
    private String token;
}
