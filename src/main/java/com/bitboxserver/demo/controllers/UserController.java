package com.bitboxserver.demo.controllers;


import com.bitboxserver.demo.models.entities.User;
import com.bitboxserver.demo.payload.response.MessageResponse;
import com.bitboxserver.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/api/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers(){
        return userService.loadAllUsers();
    }
    @PostMapping("api/users/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity removeUser(@PathVariable("username") String username){
        if(userService.removeUser(username)){
            return ResponseEntity.ok(new MessageResponse("User removed successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(("User does not exist")));

    }
}
