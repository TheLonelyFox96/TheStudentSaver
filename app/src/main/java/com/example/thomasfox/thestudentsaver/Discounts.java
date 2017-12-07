package com.example.thomasfox.thestudentsaver;

/**
 * Created by thomasfox on 20/11/2017.
 */

public class Discounts {

    private int id;
    private String discountName;
    private String clientName;
    private String description;
    private String discountCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setclientName(String clientName) {
        this.clientName = clientName;
    }


    public String getDescription() {
        return description;
    }

    public  void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
}