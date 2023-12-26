package com.example.prs.config;

import com.example.prs.models.Role;
import com.example.prs.models.Person;
import com.example.prs.repositories.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private PersonRepository userRepository;
    public CustomUserDetailsService(PersonRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {
        Person user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user with the given username");
        }
        List<Set<Role>> roles = Arrays.asList(user.getRoles());
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
        return userDetails;
    }
}
