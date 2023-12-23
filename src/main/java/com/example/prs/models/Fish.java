package com.example.prs.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "Fish")
public class Fish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина имени минимум 2 символа и максимум 30 символов")
    private String name;
    @Min(value = 0, message = "Возраст должен быть не меньше 0")
    private int years;
    @NotBlank(message = "Тип не может быть пустым")
    private String type;
    public Fish(){
    }
    public Fish(long id, String name, int years, String type) {
        this.id = id;
        this.name = name;
        this.years = years;
        this.type = type;
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
    public int getYears() {
        return years;
    }
    public void setYears(int years) {
        this.years = years;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
