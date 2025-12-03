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
    private DoctorServiceClient doctorServiceClient;



    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager ;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailId( username )
                .orElseThrow(()-> new UsernameNotFoundException("User With Email Id ==> " + username + " Not Found"));
    }

    public UserSignUpResponseDTO getUserSignUp(UserSignUpRequestDTO userSignUpRequestDTO) {

        System.out.println("Received Request ==> " + userSignUpRequestDTO);

        List<Role> userRoles = userSignUpRequestDTO.getRoles()
                .stream()
                .map((role)-> roleService.getRoleByRoleType(role))
                .collect(Collectors.toList()) ;

        User createdUser = getNewUser(
                userSignUpRequestDTO.getEmailId(),
                bcryptPasswordEncoder.encode(userSignUpRequestDTO.getPassword()),
                userRoles
        );

        userRepository.save(createdUser);

        // ----------------------------------------------------------
        //  ✔️ IF ROLE IS DOCTOR → CREATE DOCTOR PROFILE
        // ----------------------------------------------------------
        if (userSignUpRequestDTO.getRoles().contains("DOCTOR")) {

            DoctorCreateDTO doctorDto = new DoctorCreateDTO();
            doctorDto.setDoctorId(createdUser.getUserId());
            doctorDto.setFirstName("");   // empty now
            doctorDto.setLastName("");
            doctorDto.setSpecialization(null);
            doctorDto.setDepartment(null);
            doctorDto.setExperience("");
            doctorDto.setEmail(createdUser.getEmailId());
            doctorDto.setPhone("");

            try {
                doctorServiceClient.createDoctor(doctorDto);
                System.out.println("Doctor profile created successfully");
            } catch (Exception e) {
                System.out.println("Failed to create doctor profile: " + e.getMessage());
            }
        }

        return new UserSignUpResponseDTO(
                createdUser.getUserId(),
                createdUser.getEmailId()
        );
    }


    private User getNewUser(String emailId , String password , List<Role> userRoles ){
        User newUser = new User() ;
        newUser.setEmailId(emailId);
        newUser.setPassword(password);
        newUser.setRoles(userRoles);
        return newUser ;
    }

    public UserSignInResponseDTO getUserSignIn(UserSignInRequestDTO userSignInRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userSignInRequestDTO.getEmailId(),
                        userSignInRequestDTO.getPassword()
                )
        );

        User retrievedUser = (User) authentication.getPrincipal();

        System.out.println("retrievedUser ==> " + retrievedUser);

        List<String> userRoles = retrievedUser.getRoles()
                .stream()
                .map(Role::getRoleType)
                .collect(Collectors.toList());


                System.out.println("ROLES ==> " + userRoles);

        String generatedToken = jwtUtility.generateToken(
                retrievedUser.getUserId(),
                userRoles,
                retrievedUser.getEmailId()
        );

        boolean isProfileComplete = false ;
        UserSignInResponseDTO response = new UserSignInResponseDTO() ;


        if( userRoles.contains("PATIENT") ){
               System.out.println("Yes PATIENT");


            try {

                PatientProfileDTO patientProfile = patientServiceClient.checkPatientExists(retrievedUser.getUserId());

                System.out.println("patientProfile ==> " + patientProfile);
                patientProfile.setPatientId(retrievedUser.getUserId());
                patientProfile.setEmailId(retrievedUser.getEmailId());
                response.setPatientProfile(patientProfile);

                if (patientProfile != null && patientProfile.getFirstName() != null  && patientProfile.getLastName() != null && !patientProfile.getFirstName().isEmpty() && !patientProfile.getLastName().isEmpty()) {
                        isProfileComplete = true;
                        patientProfile.setProfileComplete(true);
                }

            } catch (Exception e) {
                System.out.println("Patient service error: " + e.getMessage());
                response.setPatientProfile(new PatientProfileDTO());
            }

        }
        else if( userRoles.contains("DOCTOR") ){
                
            System.out.println("Yes Doctor");

            try {
                DoctorProfileDTO doctorProfile = doctorServiceClient.checkDoctorExists(retrievedUser.getUserId());
                doctorProfile.setDoctorId(retrievedUser.getUserId());
                doctorProfile.setEmailId(retrievedUser.getEmailId());
                response.setDoctorProfile(doctorProfile);

                if (doctorProfile != null && doctorProfile.getFirstName() != null && doctorProfile.getLastName() != null  && !doctorProfile.getFirstName().isEmpty() && !doctorProfile.getLastName().isEmpty() ) {
                    isProfileComplete = true;
                    doctorProfile.setProfileComplete(true);
                }

            } catch (Exception e) {
                System.out.println("Doctor service error: " + e.getMessage());
                response.setDoctorProfile(new DoctorProfileDTO());
            }

        }
        else{

        }

        response.setJwtToken( "Bearer " + generatedToken);
        

        System.out.println( response );

        return response;
    }



    public GetAllRegisterredUsersDTO getAllRegisteredUsers() {

        List<User> registeredUsers = userRepository.findAll() ;
        return new GetAllRegisterredUsersDTO(registeredUsers);
    }
}
