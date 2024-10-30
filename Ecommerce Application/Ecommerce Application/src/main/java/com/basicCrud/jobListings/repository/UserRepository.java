package com.basicCrud.jobListings.repository;

import com.basicCrud.jobListings.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface    UserRepository extends JpaRepository<UserInfo, Long> {

//    @Query("SELECT u FROM UserModel u WHERE u.email = :email AND u.password= :password ")
//    Optional<UserModel> findByEmailAndPassword(@Param("email")String email, @Param("password")String password);

    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByUsername(String username);




}
