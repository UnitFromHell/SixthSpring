package com.example.prs.repositories;

import com.example.prs.models.Passport;
import com.example.prs.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {

    Person findByLogin(String username);
    boolean existsByPassport(Passport passport);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
