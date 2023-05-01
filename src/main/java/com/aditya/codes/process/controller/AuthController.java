package com.aditya.codes.process.controller;

import com.aditya.codes.process.codes.ApiResponse;
import com.aditya.codes.process.codes.ResetPasswordRequest;
import com.aditya.codes.process.codes.service.IAuthService;
import com.aditya.codes.process.codes.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/signup")
    ResponseEntity<ApiResponse> signup(@RequestBody SignupRequest request){
        return ResponseEntity.ok(authService.signup(request));
    }

    @GetMapping("/confirm-account")
    ResponseEntity<ApiResponse> confirmAccount(@RequestParam("id") String id,
                                               @RequestParam("field") long field){
        return ResponseEntity.ok(authService.confirmAccount(id,field));
    }

    @PostMapping("/forget-password/{email}")
    ResponseEntity<ApiResponse> forgetPassword(@PathVariable("email") String email){
        return ResponseEntity.ok(authService.forgetPassword(email));
    }

    @GetMapping(value = "/reset-page" ,produces = MediaType.TEXT_HTML_VALUE)
    ModelAndView getResetPasswordPage(@RequestParam("id") String id,
                                                      @RequestParam("field") long field){
        ModelAndView resetPasswordPage = authService.getResetPasswordPage(id, field);
        return resetPasswordPage;
    }

    @PostMapping(value = "/reset-password",produces = MediaType.TEXT_HTML_VALUE)
    ModelAndView resetPassword(
            @RequestBody ResetPasswordRequest request) {
        return authService.resetPassword(request);
    }
}
