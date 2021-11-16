package com.example.jwt.auth;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUser> selectUserFromDbByUserName(String username);

}
