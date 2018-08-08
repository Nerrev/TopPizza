package com.example.nev.toppizza.models;

import java.sql.Date;

public class Order {
    private int user;
    private int pizza;
    private int payment;
    private Date date;


    public Order(int user, int pizza, int payment, Date date) {
        setUser(user);
        setPizza(pizza);
        setPayment(payment);
        setDate(date);
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getPizza() {
        return pizza;
    }

    public void setPizza(int pizza) {
        this.pizza = pizza;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
