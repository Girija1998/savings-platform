package com.saving.service.service;

import com.saving.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.saving.service.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        System.out.println("Loading user: " + username);

        User user=userRepository
                .findByUsername(username)
                .orElse(null);
        System.out.println("User Object = " +user);

        if(user==null){
            throw new UsernameNotFoundException(
                    "User not Found"
            );
        }
        System.out.println("Username = " + user.getUsername());
        System.out.println("Password = " + user.getPassword());
        System.out.println("Role = " + user.getRole());
        return  org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
