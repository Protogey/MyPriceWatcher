package edu.utep.cs.cs4330.mypricewatcher;

import java.util.concurrent.ExecutionException;

//Find the price of the item, still a work in progress for the class because we have not gone
//over how to get prices through website
public class PriceFinder {
    private String originalPrice;
    //default constructor
    public PriceFinder(){
        originalPrice = "0.0";
    }
    //Recovery constructor, didnt have time to use it.
    public PriceFinder(String originalPrice){
        this.originalPrice = originalPrice;
    }
    //get original price
    public String getPrice(){
        return originalPrice;
    }
}
