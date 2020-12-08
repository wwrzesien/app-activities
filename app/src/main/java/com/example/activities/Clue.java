package com.example.activities;

import java.util.ArrayList;

public class Clue {
    private String name;
    private ArrayList<String> forbidden = new ArrayList<String>();

    public Clue(String _name, ArrayList<Object> _arrayList) {
        this.name = _name;
        for (Object forbiddenName : _arrayList) {
            this.forbidden.add(String.valueOf(forbiddenName));
        }

    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getArray() {
        return this.forbidden;
    }
}
