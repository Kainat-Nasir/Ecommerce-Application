package com.basicCrud.jobListings.mapper;

import com.basicCrud.jobListings.entity.UserInfo;
import com.basicCrud.jobListings.model.UserJwtResponse;
import com.basicCrud.jobListings.repository.UserRepository;
import com.basicCrud.jobListings.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
public class UserJwtMapper {


    @Autowired
    private UserRepository repo;

    public UserJwtResponse view(String token,String user) {
        Optional<UserInfo> userInformation = repo.findByUsername(user);
        UserJwtResponse userJwtResponse = new UserJwtResponse();
        userJwtResponse.setId(userInformation.get().getId());
        userJwtResponse.setUsername(userInformation.get().getUsername());
        userJwtResponse.setEmail(userInformation.get().getEmail());
        userJwtResponse.setRoles(userInformation.get().getRoles());
        userJwtResponse.setToken(token);
        return userJwtResponse;
    }

}