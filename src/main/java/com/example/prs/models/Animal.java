package com.example.prs.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "Animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина имени должна быть от 2 до 30 символов")
    private String name;

    @Min(value = 0, message = "Возраст должен быть не меньше 0")
    private int years;

    @ManyToOne
    @JoinColumn(name = "type_animal_id")
    private TypeAnimal typeAnimal;




    public Animal(){

    }
    public Animal(Long id, String name, int years, TypeAnimal typeAnimal) {
        this.id = id;
        this.name = name;
        this.years = years;
        this.typeAnimal = typeAnimal;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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

    public TypeAnimal getTypeAnimal() {
        return typeAnimal;
    }

    public void setTypeAnimal(TypeAnimal typeAnimal) {
        this.typeAnimal = typeAnimal;
    }
}
