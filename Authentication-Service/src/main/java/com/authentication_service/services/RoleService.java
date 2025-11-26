package com.authentication_service.services;

import com.authentication_service.entities.Role;
import com.authentication_service.entities.User;
import com.authentication_service.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository ;

    public Role getRoleByRoleType( String roleType ){
        return roleRepository.findByRoleType( roleType )
                .orElseThrow(()-> new EntityNotFoundException("Role With Role Type ==> " + roleType + " Not Found ")) ;

    }
}
