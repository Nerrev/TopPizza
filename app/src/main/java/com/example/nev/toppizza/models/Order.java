package com.example.nev.toppizza.models;


import java.util.Date;

public class Order {
    private int user;
    private int pizza;
    private String payment;
    private Date date;
    private String customer;
    private String odate;
    private String pizzaName;


    public Order(int user, int pizza, String payment, Date date) {
        this.user=user;
        this.pizza=pizza;
        this.payment=payment;
        this.date=date;
    }
    public Order(){

    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public String getOdate() {
        return odate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
