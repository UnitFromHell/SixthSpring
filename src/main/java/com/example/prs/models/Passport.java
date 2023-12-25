package com.example.prs.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;
@Entity
@Table(name = "passport")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Length(min = 4, max = 5)
    private String series;
    @Length(min = 6, max = 6)
    private String number;
    @OneToOne(optional = true, mappedBy = "passport")
    private Person owner;

    public Passport(long id, String series, String number, Person owner) {
        this.id = id;
        this.series = series;
        this.number = number;
        this.owner = owner;
    }

    public Passport() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
