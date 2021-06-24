package edu.utep.cs.cs4330.mypricewatcher;

import java.util.ArrayList;
import java.util.List;

/** A to-do item. */
//this class was implemented from the course website, and of course it was updated to the project
public class Item {

    /** Unique id of this item. */
    private int id;

    private String Name;
    private String URL;
    private String Price;
    private String NPrice;
    private String Percent;

    //constructors
    public Item(String Name) {
        this(Name, Name, Name, Name, Name);
    }

    public Item(String Name, String URL, String Price, String NPrice, String Percent) {
        this(0, Name, URL, Price, NPrice, Percent);
    }

    public Item(int id, String Name, String URL, String Price, String NPrice, String Percent) {
        this.id = id;
        this.Name = Name;
        this.URL = URL;
        this.Price = Price;
        this.NPrice = NPrice;
        this.Percent = Percent;
    }

    //getters
    public int id() {
        return id;
    }

    public String Name() {
        return Name;
    }

    public String URL() {
        return URL;
    }

    public String Price() {return Price; }

    public String NPrice() {return NPrice; }

    public String Percent() {return Percent; }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public void setNPrice(String NPrice) {
        this.NPrice = NPrice;
    }

    public void setPercent(String Percent) {
        this.Percent = Percent;
    }
}