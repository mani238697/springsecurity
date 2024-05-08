package com.User.SpringSecurity.services;


import com.User.SpringSecurity.entities.User;
import com.User.SpringSecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByusername( username);
        if(user==null){
            throw new UsernameNotFoundException("username or password not found");
        }
        return new CustomUserDetails(user.getUsername(),
                user.getPassword(),
                user.getFullname(),
                Authorities());
    }
    public Collection<? extends GrantedAuthority>Authorities(){
        return Arrays.asList(new SimpleGrantedAuthority("user"));
    }



}
