package com.aditya.codes.process.codes;

import com.aditya.codes.process.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Slf4j
@Component
public class ServiceHelper {

    public void validateSignupRequest(SignupRequest request) {
        log.info("validating signup request with detail :"+request);
        if(ObjectUtils.isEmpty(request)){
            throw new InvalidRequestException("Invalid request : Empty Request");
        }
        if(!StringUtils.hasText(request.getEmail())){
            throw new InvalidRequestException("Invalid request : empty email provided");
        }
        if(!StringUtils.hasText(request.getPassword())){
            throw new InvalidRequestException("Invalid request : empty password provided");
        }
    }

    public String sendActivationLink(UserInfo userInfo) {
        log.info("sending account activation link for :"+userInfo);
        String baseUrl = "http:localhost:8081/auth";
        long currentTimeStamp = LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli();
        return baseUrl +"/confirm-account?id="+userInfo.getId()+"&field="+currentTimeStamp;
    }

    public UserInfo buildUserInfo(SignupRequest request) {
        return UserInfo.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .id(UUID.randomUUID().toString().replace("-",""))
                .isActive(false)
                .build();
    }

    public String generateForgetPasswordLink(UserInfo userInfo) {
        log.info("Generating password reset link for :"+userInfo);
        String baseUrl = "http:localhost:8081/auth";
        long currentTimeStamp = LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli();
        return baseUrl +"/reset-page?id="+userInfo.getId()+"&field="+currentTimeStamp;
    }

    public void validateResetPasswordRequest(ResetPasswordRequest request) {
        if(ObjectUtils.isEmpty(request)){
            throw new InvalidRequestException("Invalid request : Empty request provided");
        }
        if(!StringUtils.hasText(request.getNewPassword())){
            throw new InvalidRequestException("Invalid request : Empty password provided");
        }
        if(!StringUtils.hasText(request.getConfirmNewPassword())){
            throw new InvalidRequestException("Invalid request : Empty password provided");
        }
        if(!request.getNewPassword().equals(request.getConfirmNewPassword())){
            throw new InvalidRequestException("Invalid request : Both passwords are different");
        }
    }
}
