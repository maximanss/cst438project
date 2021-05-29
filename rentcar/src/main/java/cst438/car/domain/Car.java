package cst438.car.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cst438.car.domain.*;

@Entity
@Table(name = "inventory")
public class Car {

    @Id
    @GeneratedValue
    private long carid;
    private String description;
    private int maxpassengers;
    private String location;
    private String image;
    private float dailyprice;
    private float weeklyprice;
    

    @ManyToOne
    @JoinColumn(name = "cartypeid", referencedColumnName = "cartypeid")
    private CarType cartype;
    
    public Car() {
    }

    public Car(long carid, String description, int maxpassengers, String location, String image,
            float dailyprice, float weeklyprice, CarType cartype) {
        super();
        this.carid = carid;
        this.description = description;
        this.maxpassengers = maxpassengers;
        this.location = location;
        this.image = image;
        this.dailyprice = dailyprice;
        this.weeklyprice = weeklyprice;
        this.cartype = cartype;
    }

    public long getCarid() {
        return carid;
    }

    public void setCarid(long carid) {
        this.carid = carid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxpassengers() {
        return maxpassengers;
    }

    public void setMaxpassengers(int maxpassengers) {
        this.maxpassengers = maxpassengers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getDailyprice() {
        return dailyprice;
    }

    public void setDailyprice(float dailyprice) {
        this.dailyprice = dailyprice;
    }

    public float getWeeklyprice() {
        return weeklyprice;
    }

    public void setWeeklyprice(float weeklyprice) {
        this.weeklyprice = weeklyprice;
    }

    public CarType getCartype() {
        return cartype;
    }

    public void setCartype(CarType cartype) {
        this.cartype = cartype;
    }

    @Override
    public String toString() {
        return "Car [carid=" + carid + ", description=" + description + ", maxpassengers="
                + maxpassengers + ", location=" + location + ", image=" + image + ", dailyprice="
                + dailyprice + ", weeklyprice=" + weeklyprice + ", cartype=" + cartype + "]";
    }


    
}