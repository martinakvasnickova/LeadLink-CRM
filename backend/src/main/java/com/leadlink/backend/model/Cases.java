package com.leadlink.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
public class Cases{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private float price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "cases", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ContactCase> contactCases;

    public Cases() {}

    public List<ContactCase> getContactCases() {
        return contactCases;
    }

    public void setContactCases(List<ContactCase> contactCases) {
        this.contactCases = contactCases;
    }

    public Cases(Long id, String name, float price, Users user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.user = user;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
