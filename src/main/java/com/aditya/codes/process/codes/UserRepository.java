package com.aditya.codes.process.codes;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserInfo,String> {
    UserInfo findByEmail(String email);
}
