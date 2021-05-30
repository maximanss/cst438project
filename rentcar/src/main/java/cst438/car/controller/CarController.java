package cst438.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import cst438.car.service.CarService;
import cst438.car.domain.*;

@Controller
public class CarController {
    
    @Autowired
    CarService carService;
    
    // URL like localhost:8080/cst438.carrentals.com/carchoice?location=Boston
    @GetMapping("/cst438.carrentals.com/carchoice")
    public String displayCarChoices(
            @RequestParam(value="location") String loc,
            @RequestParam(value="startdate", defaultValue="") String start,
            @RequestParam(value="enddate", defaultValue="") String end,
            Model model) {
       
       Iterable<Car> cars = carService.getAvailableCars(loc, start, end);
       model.addAttribute("cars", cars);
        return "car_choice";
    }

}
