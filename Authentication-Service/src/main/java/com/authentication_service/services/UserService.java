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
    private PatientServiceClient patientServiceClient;


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

        System.out.println("Received Request ==> " + userSignInRequestDTO);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userSignInRequestDTO.getEmailId(),
                        userSignInRequestDTO.getPassword()
                )
        );

        User retrievedUser = (User) authentication.getPrincipal();

        System.out.println("Retrieved User ==> " + retrievedUser);

        List<String> userRoles = retrievedUser
                .getRoles()
                .stream()
                .map(Role::getRoleType)
                .collect(Collectors.toList());

        String generatedToken = jwtUtility.generateToken(
                retrievedUser.getUserId(),
                userRoles,
                retrievedUser.getEmailId()
        );

        System.out.println("Generated Token ==> " + generatedToken);

        // ----------------------------------------------------------
        //  ✔️ CALL PATIENT SERVICE USING FEIGN CLIENT
        // ----------------------------------------------------------
        PatientDTO patientProfile = null;
        boolean isProfileComplete = false;

        try {
            patientProfile = patientServiceClient.checkPatientExists(retrievedUser.getUserId());

            // Check if profile is complete
            if (patientProfile != null &&
                    patientProfile.getName() != null &&
                    !patientProfile.getName().isEmpty()) {
                isProfileComplete = true;
            }
        } catch (Exception e) {
            System.out.println("Error calling Patient Service: " + e.getMessage());
            patientProfile = new PatientDTO(); // fallback
        }

        // ----------------------------------------------------------
        //  ✔️ RETURN FULL RESPONSE
        // ----------------------------------------------------------
        return new UserSignInResponseDTO(
                retrievedUser.getEmailId(),
                "Bearer " + generatedToken,
                retrievedUser.getUserId(),
                userRoles,
                isProfileComplete,
                patientProfile
        );
    }


    public GetAllRegisterredUsersDTO getAllRegisteredUsers() {

        List<User> registeredUsers = userRepository.findAll() ;
        return new GetAllRegisterredUsersDTO(registeredUsers);
    }
}
