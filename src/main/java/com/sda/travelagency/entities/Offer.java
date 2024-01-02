package com.sda.travelagency.entities;

import com.sda.travelagency.exception.OfferSoldOutException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    private BigDecimal price;

    private int userId;

    private int quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;


    public Offer(String name, BigDecimal price, int quantity, Hotel hotel) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.hotel = hotel;
    }

    public Offer() {
    }


    public String getUserName() {return userName; }
    public String getName() {
        return name;
    }
    public Hotel getHotel() {
        return hotel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void reserve(){
        if(quantity > 0){
            quantity--;
        } else {
            throw new OfferSoldOutException("Offer sold out");
        }

    }
    public void release(){
            quantity++;
    }

}
