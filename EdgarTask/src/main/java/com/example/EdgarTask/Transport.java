package com.example.EdgarTask;

public class Transport {
    private int ID;
    private String brand;
    private String model;
    private String category;
    private String number;
    private int year;
    private boolean isTrailer;

    public Transport(int ID, String brand, String model, String category, String number, int year, boolean isTrailer) {
        this.ID = ID;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.number = number;
        this.year = year;
        this.isTrailer = isTrailer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isTrailer() {
        return isTrailer;
    }

    public void setTrailer(boolean trailer) {
        isTrailer = trailer;
    }
}
