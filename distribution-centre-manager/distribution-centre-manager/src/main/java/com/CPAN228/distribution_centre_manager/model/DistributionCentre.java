package com.CPAN228.distribution_centre_manager.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class DistributionCentre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private double latitude;
    private double longitude;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> itemsAvailable;

    // Getters and Setters
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Item> getItemsAvailable() {
        return itemsAvailable;
    }

    public void setItemsAvailable(List<Item> itemsAvailable) {
        this.itemsAvailable = itemsAvailable;
    }
}
