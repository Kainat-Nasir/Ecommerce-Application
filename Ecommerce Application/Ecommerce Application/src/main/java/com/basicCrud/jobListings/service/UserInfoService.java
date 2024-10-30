package com.basicCrud.jobListings.service;

import com.basicCrud.jobListings.entity.AuthRequest;
import com.basicCrud.jobListings.entity.UserInfo;
import com.basicCrud.jobListings.mapper.UserJwtMapper;
import com.basicCrud.jobListings.model.UserJwtResponse;
import com.basicCrud.jobListings.model.UserResponse;
import com.basicCrud.jobListings.repository.UserRepository;
import com.basicCrud.jobListings.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserInfoService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private UserJwtMapper userJwtMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public Long getUserIdByUsername(String username) {
        Optional<UserInfo> user = repo.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getId();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
    public List<UserInfo> showAllUsers() {
        return repo.findAll();
    }

    public Optional<UserInfo> getUserById(Long id) {
        return repo.findById(id);
    }

    public UserResponse findUserModelByEmail(String email, String password) {
//        Optional<UserModel> mail= repo.findByEmailAndPassword(email,password);
        Optional<UserInfo> user = repo.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {

            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.get().getId());
            userResponse.setName(user.get().getUsername());
//           userResponse.setPhone(user.get().getPhone());
            userResponse.setEmail(user.get().getEmail());


            return userResponse;
        }


        return null;
    }

    public UserInfo addUserData(UserInfo user) {
        return repo.save(user);

    }

    public UserInfo updateUser(UserInfo user) {
        UserInfo updatinguser = repo.findById(user.getId()).get();
        updatinguser.setUsername(user.getUsername());
        updatinguser.setEmail(user.getEmail());
        updatinguser.setPassword(user.getPassword());
        return repo.save(updatinguser);
    }

    public void DeleteUser(Long id) {
        Long authenticatedUserId = SecurityUtil.getCurrentUserId();

        // Fetch the todo that is being requested for deletion
        Optional<UserInfo> UserOptional = repo.findById(id);

        if (UserOptional.isPresent()) {
            UserInfo Userinfo = UserOptional.get();

            // Check if the authenticated user is the owner of the todo
            if (Userinfo.getId().equals(authenticatedUserId)) {
                // If the user is the owner, proceed with deletion
                repo.deleteById(id);
            } else {
                // If the user is not the owner, throw an exception or return an error response
                throw new UnauthorizedException("You are not authorized to delete this todo.");
            }
        } else {
            throw new ResourceNotFoundException("Todo not found with ID: " + id);
        }

    }

    public UserInfo findById(Long id) {
        return repo.findById(id).get();
    }


    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repo.save(userInfo);
        return "User Added Successfully";
    }


    public UserJwtResponse viewData(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            UserInfo userInfo = repo.findByUsername(authRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String token = jwtService.generateToken(authRequest.getUsername(), userInfo.getId() , userInfo.getRoles()); // Pass user ID
            String username = authRequest.getUsername();
            return userJwtMapper.view(token, username);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
