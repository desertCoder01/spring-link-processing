package com.aditya.codes.process.codes;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String id;
    private long field;
    private String newPassword;
    private String confirmNewPassword;
}
