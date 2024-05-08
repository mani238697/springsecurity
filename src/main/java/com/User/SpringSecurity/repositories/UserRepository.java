package com.User.SpringSecurity.repositories;

import com.User.SpringSecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByusername(String username);

    User findByusernameAndPassword(String username, String password);

    User findByResetToken(String resetToken); // If not already added


}
