package com.example.prs.repositories;

import com.example.prs.models.Fish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishRepository extends JpaRepository<Fish,Long> {
}
