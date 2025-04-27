package com.leadlink.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Entity reprezentující obchodní případ (Case) v systému.
 * Každý případ je propojen s uživatelem (vlastníkem) a může být spojen s více kontakty.
 */

@Entity
public class Cases{
    @Id
    @GeneratedValue
    private Long id; // Unikátní identifikátor případu
    private String name; // Název případu
    private float price; // Cena případu

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user; // Vlastník případu

    @OneToMany(mappedBy = "cases", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ContactCase> contactCases; // Seznam vazeb na kontakty

    /**
     * Výchozí konstruktor.
     */
    public Cases() {}


    /**
     * Konstruktor s parametry.
     *
     * @param id    ID případu
     * @param name  Název případu
     * @param price Cena případu
     * @param user  Vlastník případu
     */
    public Cases(Long id, String name, float price, Users user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.user = user;
    }

    // Gettery a settery

    public List<ContactCase> getContactCases() {
        return contactCases;
    }

    public void setContactCases(List<ContactCase> contactCases) {
        this.contactCases = contactCases;
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
