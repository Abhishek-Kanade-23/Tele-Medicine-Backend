package com.authentication_service.dtos;

import com.authentication_service.entities.User;

import java.util.List;

public class GetAllRegisterredUsersDTO {

    private List<User> user ;

    public GetAllRegisterredUsersDTO() {
    }

    public GetAllRegisterredUsersDTO(List<User> user) {
        this.user = user;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GetAllRegisterredUsersDTO{" +
                "user=" + user +
                '}';
    }
}
