package com.bitboxserver.demo.services;

import com.bitboxserver.demo.Repositories.RoleRepository;
import com.bitboxserver.demo.Repositories.UserRepository;
import com.bitboxserver.demo.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public List<User> loadAllUsers(){
        return userRepository.findAll();
    }

    public Boolean userExists(String username){
        return userRepository.existsByUsername(username);
    }
    public Boolean removeUser(String username){
        if(userExists(username)){
            userRepository.delete(userRepository.findByUsername(username).get());
            return true;
        }
        return false;
    }
}
