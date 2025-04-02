package com.leadlink.backend.dto;

public class CaseDTO {
    private Long id;
    private String name;
    private double price;
    private String contactName;


    public CaseDTO(Long id, String name, double price, String contactName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.contactName = contactName;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
