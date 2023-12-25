package com.example.prs.repositories;

import com.example.prs.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament,Long> {
}
