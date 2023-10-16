package com.test.apiJava.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.apiJava.exception.ErrorResponse;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping(value = "/user")
public class UserController {

  @Autowired
  private IUserRepository userRepository;

  @PostMapping()
  public ResponseEntity create(@RequestBody UserModel userModel) {

    var user = this.userRepository.findByUsername(userModel.getUsername());
    
    if(user != null) {
      var status = 400;
      return ResponseEntity.status(status).body(new ErrorResponse(status, "Usuário já exite."));
    }

    var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
    
    userModel.setPassword(passwordHashred);
    
    var userCreated = this.userRepository.save(userModel);
    return ResponseEntity.status(201).body(userCreated);
  }
}
