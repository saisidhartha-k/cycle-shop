package com.cycle.rental.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Bag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

 

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

 

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bag_item_id")
    private List<Items> items = new ArrayList<>();
    
    private long totalAmount;
}
