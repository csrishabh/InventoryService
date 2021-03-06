package com.mongo.demo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongo.demo.document.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String email);
    
    @Query(value="{'fullname' : {$regex : ?0 , $options: 'i'}, roles: { $elemMatch: { $in: [?1] } }}" , fields="{ fullname: 1, username:1 }")
    List<User> findByNameStartingWithAndType(String regexp, String role);
    
    @Query(value="{'fullname' : {$regex : ?0 , $options: 'i'}}" , fields="{ fullname: 1 }")
    List<User> findByNameStartingWith(String regexp);

}
