package com.example.prs.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private String name;

    @Min(value = 1000, message = "Призовой фонд должен быть не меньше 1000")
    private double found;

    @ManyToMany
    @JoinTable(
            name = "tournamentInTeam",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams;

    public  Tournament(){

    }
    public Tournament(long id, String name, double found, List<Team> teams) {
        this.id = id;
        this.name = name;
        this.found = found;
        this.teams = teams;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFound() {
        return found;
    }

    public void setFound(double found) {
        this.found = found;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
