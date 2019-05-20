package com.example.nev.toppizza.services;


import com.example.nev.toppizza.models.Pizza;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PizzaJasonParser {

    public static List<Pizza> getObjectFromJason(String jason) {
        List<Pizza> Pizzas ;
        try {
//            if (jason != null && jason != "") {
                JSONArray jsonArray = new JSONArray(jason);
                Pizzas = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject;
                    jsonObject = (JSONObject) jsonArray.get(i);
                    String[] price = new String[3];
                    price[0] = jsonObject.getString("smallPrice");
                    price[1] = jsonObject.getString("medium");
                    price[2] = jsonObject.getString("largePrice");
                    Pizza Pizza = new Pizza(jsonObject.getString("name"), jsonObject.getString("summary"), jsonObject.getString("type"), price, jsonObject.getString("offer").toString());
                    Pizzas.add(Pizza);
                }
//            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return Pizzas;
    }
}
