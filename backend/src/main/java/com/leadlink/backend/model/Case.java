package com.leadlink.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Case {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private float price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

}
