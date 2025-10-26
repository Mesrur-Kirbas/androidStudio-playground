package com.example.landmarkbookjava;

import java.io.Serializable;

public class Landmark implements Serializable {
    String name;
    String location;
    int image;



    public Landmark(String name, String location, int image){
        this.name = name;
        this.location = location;
        this.image = image;
    }
}

