package com.basicCrud.jobListings.service;

import com.basicCrud.jobListings.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserInfoDetails implements UserDetails {
    private UserInfo userInfo;
    private String name;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private String passord;

    private List<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfo userInfo) {
        this.userInfo= userInfo;
        name=userInfo.getUsername();
        passord=userInfo.getPassword();
        authorities= Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }



    @Override
    public String getPassword() {
        return passord;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
