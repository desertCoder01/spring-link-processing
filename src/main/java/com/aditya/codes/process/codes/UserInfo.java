package com.aditya.codes.process.codes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "user_info")
public class UserInfo {

    @Id
    private String id;

    private String email;

    private String password;

    private boolean isActive;
}
