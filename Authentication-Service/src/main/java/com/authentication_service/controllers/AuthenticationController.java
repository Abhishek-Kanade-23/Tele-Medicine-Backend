package com.authentication_service.controllers;


import com.authentication_service.dtos.*;
import com.authentication_service.entities.User;
import com.authentication_service.services.DoctorServiceClient;

import com.authentication_service.services.PatientServiceClient;
import com.authentication_service.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService ;

    @Autowired
    private PatientServiceClient patientServiceClient;

    @GetMapping("/home")
    public ResponseEntity getHomePage(){
        return ResponseEntity.ok("Home Page") ;
    }

    @Autowired
    private DoctorServiceClient doctorServiceClient;



    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDTO> getUserSignUp(@Valid @RequestBody UserSignUpRequestDTO userSignUpRequestDTO ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        userService.getUserSignUp( userSignUpRequestDTO )
                ) ;
    }

    @PostMapping("/signin")
    public ResponseEntity<UserSignInResponseDTO> getUSerSignIn(@Valid @RequestBody UserSignInRequestDTO userSignInRequestDTO ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        userService.getUserSignIn( userSignInRequestDTO )
                ) ;

    }


    @GetMapping("/users")
    public ResponseEntity<GetAllRegisterredUsersDTO> getAllRegisteredUsers(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        userService.getAllRegisteredUsers()
                ) ;
    }


}
