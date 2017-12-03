package com.example.actech.digitecsoln;

/**
 * Created by ACTECH on 12/3/2017.
 */

public class Hotels {

    public String name,rating,types,vicinity;

    public Hotels(String name,String rating,String types,String vicinity){
    /*    this.setName(name);
        this.setRating(rating);
        this.setTypes(types);
        this.setVicinity(vicinity);
        */
    this.name=name;
    this.rating=rating;
    this.types=types;
    this.vicinity=vicinity;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){

    }
    public String getRating(){
        return rating;
    }
    public void setRating(String rating){

    }
    public String getTypes(){
        return types;
    }
    public void setTypes(String types){

    }
    public String getVicinity(){
        return vicinity;
    }
    public void setVicinity(String vicinity){

    }
}
