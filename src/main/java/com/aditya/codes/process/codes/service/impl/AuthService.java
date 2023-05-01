package com.aditya.codes.process.codes.service.impl;

import com.aditya.codes.process.codes.*;
import com.aditya.codes.process.codes.service.IAuthService;
import com.aditya.codes.process.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService implements IAuthService {

    private final ServiceHelper serviceHelper;
    private final UserRepository userRepository;

    @Override
    public ApiResponse signup(SignupRequest request) {
        log.info("inside signup method ith details :"+request);
        serviceHelper.validateSignupRequest(request);
        UserInfo userInfo = userRepository.findByEmail(request.getEmail());

        if(!ObjectUtils.isEmpty(userInfo)){
            if(userInfo.isActive()){
                throw new InvalidRequestException("Account already exist " +
                        "with this email :"+request.getEmail());
            }
            userInfo.setPassword(request.getPassword());
            String link = serviceHelper.sendActivationLink(userInfo);
            userRepository.save(userInfo);
            return new ApiResponse("Account activation link sent : "+link,true);
        }
        userInfo = serviceHelper.buildUserInfo(request);
        String newLink = serviceHelper.sendActivationLink(userInfo);
        userRepository.save(userInfo);
        return new ApiResponse("Account activation link sent :"+newLink,true);
    }

    @Override
    public ApiResponse confirmAccount(String id, long field) {
        log.info("Received account activation request for :"+id);
        Optional<UserInfo> optionalUserInfo = userRepository.findById(id);
        if(ObjectUtils.isEmpty(optionalUserInfo)){
            throw new InvalidRequestException("Invalid link : No details found");
        }
        UserInfo userInfo = optionalUserInfo.get();
        if(userInfo.isActive()){
            throw new InvalidRequestException("Account already activated");
        }
        long currentTimeStamp = LocalDateTime.now(ZoneOffset.UTC)
                .toInstant(ZoneOffset.UTC).toEpochMilli();
        if((field+3600000) < currentTimeStamp){
            throw new InvalidRequestException("Link has expired");
        }
        userInfo.setActive(true);
        userRepository.save(userInfo);
        return new ApiResponse("Account activated for :"+id,true);
    }

    @Override
    public ApiResponse forgetPassword(String email) {
        log.info("Received forget password request for :"+email);
        UserInfo userInfo = userRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(userInfo)){
            throw new InvalidRequestException("Invalid request : No details found for :"+email);
        }
        String link = serviceHelper.generateForgetPasswordLink(userInfo);
        return new ApiResponse("Password reset link sent : "+link,true);
    }

    @Override
    public ModelAndView getResetPasswordPage(String id, long field) {
        log.info("Received get reset password page for :"+id);
        Optional<UserInfo> optionalUserInfo = userRepository.findById(id);
        if(ObjectUtils.isEmpty(optionalUserInfo)){
            throw new InvalidRequestException("Invalid link : No details found");
        }
        long currentTimeStamp = LocalDateTime.now(ZoneOffset.UTC)
                .toInstant(ZoneOffset.UTC).toEpochMilli();
        if(field+3600000 < currentTimeStamp){
            throw new InvalidRequestException("Link has expired");
        }
        ModelAndView modelAndView = new ModelAndView("reset-password");
        modelAndView.addObject("id", id);
        modelAndView.addObject("field", field);
        return modelAndView;
    }

    @Override
    public ModelAndView resetPassword(ResetPasswordRequest request) {
        try {
            serviceHelper.validateResetPasswordRequest(request);
            Optional<UserInfo> optionalUserInfo = userRepository.findById(request.getId());
            if(ObjectUtils.isEmpty(optionalUserInfo)){
                throw new InvalidRequestException("Invalid request : User not found");
            }
            long currentTimeStamp = LocalDateTime.now(ZoneOffset.UTC)
                    .toInstant(ZoneOffset.UTC).toEpochMilli();
            if(request.getField()+3600000 < currentTimeStamp){
                throw new InvalidRequestException("Link has expired");
            }
            UserInfo userInfo = optionalUserInfo.get();
            userInfo.setPassword(request.getNewPassword());
            userRepository.save(userInfo);
            ModelAndView modelAndView = new ModelAndView("password-reset-success");
            return modelAndView;
        }catch (Exception ex){
            ModelAndView modelAndView = new ModelAndView("password-reset-fail");
            modelAndView.addObject("message",ex.getMessage());
            return modelAndView;
        }
    }
}
