package com.example.prs.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Название команды не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина названия минимум 2 символа и максимум 30 символов")
    private String name;
    @NotBlank(message = "Тип команды не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина типа минимум 2 символа и максимум 30 символов")
    private String type;
    @NotBlank(message = "Название лиги не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина лиги минимум 2 символа и максимум 30 символов")
    private String league;

    public Team(){
    }
    public Team(long id, String name, String type, String league) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.league = league;
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getLeague() {
        return league;
    }
    public void setLeague(String league) {
        this.league = league;
    }
}
