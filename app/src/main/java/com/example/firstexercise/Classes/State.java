package com.example.firstexercise.Classes;

import java.io.Serializable;
import java.util.ArrayList;


public class State implements Serializable {

    private String name;
    private ArrayList<String> borders = null;
    private String nativeName;
    private String flag;

    public State(String name, String nativeName) {
        this.name = name;
        this.nativeName = nativeName;
    }

    public State(String name, ArrayList<String> borders, String nativeName, String flag) {
        this.name = name;
        this.borders = borders;
        this.nativeName = nativeName;
        this.flag = flag;
    }

    public State(String name, String nativeName, String flag) {
        this.name = name;
        this.nativeName = nativeName;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getFlag() {
        return flag;
    }
}
