package com.example.prs.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина имени минимум 2 символа и максимум 30 символов")
    private String name;

    @Min(value = 0, message = "Возраст должен быть не меньше 0")
    private int age;
    @Column(unique = true)
    @NotBlank(message = "Емейл не может быть пустым")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$", message = "Некорректный формат емейла")
    @Size(min = 2, max = 30, message = "Длина типа минимум 2 символа и максимум 30 символов")
    private String email;

    @Column(unique = true)
    @NotBlank(message = "Логин не может быть пустым")
    private String login;
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="passport_id", unique = true)
    private Passport passport;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    public Person(){
    }
public Person(long  id, String name,  String email, int age, String login, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.age = age;
    this.login = login;
    this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
