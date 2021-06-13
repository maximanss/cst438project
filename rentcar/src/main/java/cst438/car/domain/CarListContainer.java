package cst438.car.domain;

import java.util.List;

// This is a helper class for list of cars

public class CarListContainer {
    private List<Car> carlist;
    
    public CarListContainer() {
        
    }

    public CarListContainer(List<Car> carlist) {
        super();
        this.carlist = carlist;
    }

    public List<Car> getCarlist() {
        return carlist;
    }

    public void setCarlist(List<Car> carlist) {
        this.carlist = carlist;
    }

    @Override
    public String toString() {
        return "CarListContainer [carlist=" + carlist + "]";
    }
    
    

}
