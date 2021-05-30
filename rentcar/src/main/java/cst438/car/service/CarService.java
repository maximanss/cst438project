package cst438.car.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import cst438.car.domain.*;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;
    
    public List<Car> getAvailableCars(String location, String start, String end) {
            
        List<Car> cars = carRepository.findByLocation(location);
            
        return cars;
            
    }
}
