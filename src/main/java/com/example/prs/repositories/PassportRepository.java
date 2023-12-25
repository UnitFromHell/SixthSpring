package com.example.prs.repositories;

import com.example.prs.models.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport,Long> {
}
