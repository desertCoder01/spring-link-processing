package com.aditya.codes.process.codes.service;

import com.aditya.codes.process.codes.ApiResponse;
import com.aditya.codes.process.codes.ResetPasswordRequest;
import com.aditya.codes.process.codes.SignupRequest;
import org.springframework.web.servlet.ModelAndView;

public interface IAuthService {
    ApiResponse signup(SignupRequest request);
    ApiResponse confirmAccount(String id, long field);
    ApiResponse forgetPassword(String email);
    ModelAndView getResetPasswordPage(String id, long field);

    ModelAndView resetPassword(ResetPasswordRequest request);
}
