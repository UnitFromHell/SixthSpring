package com.example.prs.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "TypeAnimal")
public class TypeAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Вид не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина типа должна быть от 2 до 30 символов")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @OneToMany(mappedBy = "typeAnimal")
    private List<Animal> animalItems;




    public TypeAnimal(){

    }
    public TypeAnimal(Long id, String name, String description, List<Animal> animalItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.animalItems = animalItems;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Animal> getAnimalItems() {
        return animalItems;
    }

    public void setAnimalItems(List<Animal> animalItems) {
        this.animalItems = animalItems;
    }
}
