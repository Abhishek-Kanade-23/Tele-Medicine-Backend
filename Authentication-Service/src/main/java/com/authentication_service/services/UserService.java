package com.authentication_service.services;

import com.authentication_service.dtos.UserSignUpRequestDTO;
import com.authentication_service.dtos.UserSignUpResponseDTO;
import com.authentication_service.entities.Role;
import com.authentication_service.entities.User;
import com.authentication_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
