package com.basicCrud.jobListings.Controller;

import com.basicCrud.jobListings.entity.AuthRequest;
import com.basicCrud.jobListings.entity.UserInfo;
import com.basicCrud.jobListings.model.User;
import com.basicCrud.jobListings.model.UserJwtResponse;
import com.basicCrud.jobListings.model.UserResponse;
import com.basicCrud.jobListings.service.JwtService;
import com.basicCrud.jobListings.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(value= "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/alluser")
//    @PreAuthorize("hasAuthority('Admin')")
    public List<UserInfo> showAllUsers() {
        return userInfoService.showAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<UserInfo> getUserById(@PathVariable Long id) {
        return userInfoService.getUserById(id);
    }


    @PostMapping("/findbyemail")
    public ResponseEntity<UserResponse> findUserModelByEmail(@RequestBody User user) {
        UserResponse userResponse = userInfoService.findUserModelByEmail(user.getEmail(), user.getPassword());
        if (userResponse != null) {
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.status(401).build(); // 401 Unauthorized
        }

    }

    @PostMapping("/add")
    public UserInfo addUser(@RequestBody UserInfo user) {
        return userInfoService.addUserData(user);
    }

    @PutMapping("/update")
    public UserInfo updateUser(@RequestBody UserInfo user) {
        return userInfoService.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userInfoService.DeleteUser(id);
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {

        if (userInfo.getRoles() == null || userInfo.getRoles().isEmpty()) {
            userInfo.setRoles("User");
        }
        String response = userInfoService.addUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> addNewAdmin(@RequestBody UserInfo adminInfo) {
        adminInfo.setRoles("Admin");
        String response = userInfoService.addUser(adminInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    public Optional<UserJwtResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return Optional.ofNullable(userInfoService.viewData(authRequest));
    }


}
