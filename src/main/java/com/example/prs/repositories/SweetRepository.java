package com.example.prs.repositories;

import com.example.prs.models.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SweetRepository extends JpaRepository<Sweet,Long> {
}
