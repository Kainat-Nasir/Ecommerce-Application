package com.basicCrud.jobListings.util;

import com.basicCrud.jobListings.service.UserInfoDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {


    public static Long getCurrentUserId()
    {
        UserInfoDetails userId= (UserInfoDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id=userId.getUserInfo().getId();
        return id;

    }
}
//    public static UserInfoDetails getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof UserInfoDetails) {
//            return (UserInfoDetails) authentication.getPrincipal();
//        }
//        throw new RuntimeException("User is not authenticated");
//    }

    // Method to get the current user's ID
//    public static Long getCurrentUserId() {
//        Long user = getCurrentUser();
//        return user.getUserInfo().getId();
//    }


