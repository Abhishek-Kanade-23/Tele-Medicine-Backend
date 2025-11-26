package com.authentication_service.services;

import com.authentication_service.dtos.*;
import com.authentication_service.entities.Role;
import com.authentication_service.entities.User;
import com.authentication_service.repositories.UserRepository;
import com.authentication_service.utils.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private RoleService roleService ;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder ;

    @Autowired
    private JwtUtility jwtUtility ;


    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager ;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailId( username )
                .orElseThrow(()-> new UsernameNotFoundException("User With Email Id ==> " + username + " Not Found"));
    }

    public UserSignUpResponseDTO getUserSignUp(UserSignUpRequestDTO userSignUpRequestDTO) {

        System.out.println("Received Request ==> " + userSignUpRequestDTO );

        List<Role> userRoles = userSignUpRequestDTO.getRoles()
                .stream()
                .map((role)-> roleService.getRoleByRoleType(role))
                .collect(Collectors.toList()) ;

        User createdUser = getNewUser(
                userSignUpRequestDTO.getEmailId() ,
                bcryptPasswordEncoder.encode( userSignUpRequestDTO.getPassword() ),
                userRoles

        ) ;


        userRepository.save(createdUser) ;


        return new UserSignUpResponseDTO(
                createdUser.getUserId(),
                createdUser.getEmailId()
        ) ;
    }

    private User getNewUser(String emailId , String password , List<Role> userRoles ){
        User newUser = new User() ;
        newUser.setEmailId(emailId);
        newUser.setPassword(password);
        newUser.setRoles(userRoles);
        return newUser ;
    }

    public UserSignInResponseDTO getUserSignIn(UserSignInRequestDTO userSignInRequestDTO) {
        System.out.println("Received Request ==> " + userSignInRequestDTO );

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                       userSignInRequestDTO.getEmailId() ,
                       userSignInRequestDTO.getPassword()
                )
        ) ;

        User retrivedUser = (User) authentication.getPrincipal() ;

        System.out.println("Retrived User ==> " + retrivedUser );

        List<String> userRoles = retrivedUser
                .getRoles()
                .stream()
                .map((role)-> role.getRoleType())
                .collect(Collectors.toList());

        String generatedToken = (String) jwtUtility.generateToken(retrivedUser.getUserId(),userRoles, retrivedUser.getEmailId() );


        System.out.println("Generated Token ==> " + generatedToken);



        return new UserSignInResponseDTO(retrivedUser.getEmailId(), generatedToken , retrivedUser.getUserId(),userRoles) ;
    }

    public GetAllRegisterredUsersDTO getAllRegisteredUsers() {

        List<User> registeredUsers = userRepository.findAll() ;
        return new GetAllRegisterredUsersDTO(registeredUsers);
    }
}
