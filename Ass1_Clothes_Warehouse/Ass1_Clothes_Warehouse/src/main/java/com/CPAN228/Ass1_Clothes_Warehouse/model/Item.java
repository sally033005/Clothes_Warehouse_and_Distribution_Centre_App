package com.CPAN228.Ass1_Clothes_Warehouse.model;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "items")  
public class Item {
    private static long counter = 0;

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private long id;

    private String name;

    private String brand;

    @Column(name = "manufacture_year")  
    private int year;

    private double price;

    public Item() {
        
    }

    public Item(String name, String brand, int year, double price) {
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.price = price;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    
    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setYear(int year) { this.year = year; }
    public void setPrice(double price) { this.price = price; }

}
