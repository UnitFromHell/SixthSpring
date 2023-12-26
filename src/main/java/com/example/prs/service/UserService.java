package com.example.prs.service;

import com.example.prs.models.Person;
import com.example.prs.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService  {
    private final PersonRepository userRepository;
    public UserService(PersonRepository userRepository){
        this.userRepository = userRepository;
    }

    public Person getUserByLogin(String login){
        Person user = userRepository.findByLogin(login);
        return user;
    }
    public Person createUser(Person user){
        Person newUser = userRepository.save(user);
        userRepository.flush();
        return newUser;
    }
}