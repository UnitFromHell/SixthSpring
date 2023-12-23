package com.example.prs.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "Sweet")
public class Sweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Название конфеты не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина названия минимум 2 символа и максимум 30 символов")
    private String name;
    @Min(value = 0, message = "Количество должен быть не меньше 0")
    private int kolvo;
    @NotBlank(message = "Тип не может быть пустым")
    private String type;

    public Sweet(){
    }
    public Sweet(long id, String name, int kolvo, String type) {
        this.id = id;
        this.name = name;
        this.kolvo = kolvo;
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
    public int getKolvo() {
        return kolvo;
    }
    public void setKolvo(int kolvo) {
        this.kolvo = kolvo;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
