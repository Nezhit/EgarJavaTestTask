package com.example.EdgarTask.classes;
import jakarta.persistence.*;

@Entity
@Table(name = "transport")
public class Transport {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long ID;
    @Column(name="brand")
    private String brand;
    @Column(name="model")
    private String model;
    @Column(name="category")
    private String category;
    @Column(name="number")
    private String number;
    @Column(name="year")
    private int year;
    @Column(name="istrailer")
    private boolean isTrailer;
    protected Transport(){}
    public Transport(long ID, String brand, String model, String category, String number, int year, boolean isTrailer) {
        this.ID = ID;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.number = number;
        this.year = year;
        this.isTrailer = isTrailer;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
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
