package com.User.SpringSecurity.services;

import com.User.SpringSecurity.entities.User;
import com.User.SpringSecurity.repositories.UserRepository;
import jakarta.websocket.EncodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import jakarta.websocket.Encoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return (User) userRepository.findById(id).orElse(null);
    }




    public User saveUser(User user) {
        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }




    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
        // resetIdSequence();
    }

    public User getUserByusernameAndPassword(String username, String password) {
        return userRepository.findByusernameAndPassword(username, password);
    }
    public User getUserByUsername(String username) {
        return userRepository.findByusername(username);
    }




    public boolean isUserExists(String username) {
        // Check if a user with the given email already exists in the database
        return userRepository.findByusername(username) != null;
    }



}

