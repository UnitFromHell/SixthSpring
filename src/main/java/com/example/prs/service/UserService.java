package com.example.prs.service;

import com.example.prs.models.Person;
import com.example.prs.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = personRepository.findByLogin(username);
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getLogin())
                        .password(user.getPassword())
                        .roles("USER")
                        .build();
        return userDetails;
    }
}