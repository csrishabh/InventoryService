package com.mongo.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.demo.document.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String email);

}
