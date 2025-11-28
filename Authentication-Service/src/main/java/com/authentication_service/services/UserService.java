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

        List<String> userRoles = retrievedUser.getRoles()
                .stream()
                .map(Role::getRoleType)
                .collect(Collectors.toList());

        String generatedToken = jwtUtility.generateToken(
                retrievedUser.getUserId(),
                userRoles,
                retrievedUser.getEmailId()
        );

        UserSignInResponseDTO response = new UserSignInResponseDTO(
                retrievedUser.getEmailId(),
                "Bearer " + generatedToken,
                retrievedUser.getUserId(),
                userRoles
        );

        boolean isProfileComplete = false;

        // ----------------------------------------------------------
        //    ROLE: PATIENT
        // ----------------------------------------------------------
        if (userRoles.contains("PATIENT")) {

            try {
                PatientDTO patientProfile = patientServiceClient.checkPatientExists(retrievedUser.getUserId());
                response.setPatientProfile(patientProfile);

                if (patientProfile != null &&
                        patientProfile.getName() != null &&
                        !patientProfile.getName().isEmpty()) {
                    isProfileComplete = true;
                }

            } catch (Exception e) {
                System.out.println("Patient service error: " + e.getMessage());
                response.setPatientProfile(new PatientDTO());
            }

        }

        // ----------------------------------------------------------
        //    ROLE: DOCTOR
        // ----------------------------------------------------------
        if (userRoles.contains("DOCTOR")) {

            try {
                DoctorResponseDTO doctorProfile = doctorServiceClient.checkDoctorExists(retrievedUser.getUserId());
                response.setDoctorProfile(doctorProfile);

                if (doctorProfile != null &&
                        doctorProfile.getFirstName() != null &&
                        !doctorProfile.getFirstName().isEmpty()) {
                    isProfileComplete = true;
                }

            } catch (Exception e) {
                System.out.println("Doctor service error: " + e.getMessage());
                response.setDoctorProfile(new DoctorResponseDTO());
            }

        }

        response.setProfileComplete(isProfileComplete);
        return response;
    }



    public GetAllRegisterredUsersDTO getAllRegisteredUsers() {

        List<User> registeredUsers = userRepository.findAll() ;
        return new GetAllRegisterredUsersDTO(registeredUsers);
    }
}
