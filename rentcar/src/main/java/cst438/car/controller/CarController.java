package cst438.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
            @RequestParam(value="userid", defaultValue="") String userid,
            @RequestParam(value="location") String loc,
            @RequestParam(value="startdate", defaultValue="") String start,
            @RequestParam(value="enddate", defaultValue="") String end,
            Model model) {
       
       Iterable<Car> cars = carService.getAvailableCars(loc, start, end);
       model.addAttribute("userid", userid);
       model.addAttribute("startdate", start);
       model.addAttribute("enddate", end);
       model.addAttribute("cars", cars);
        return "car_choice";
    }
    
    //Request Method mapping for different select button
    @RequestMapping(value="/cst438.carrentals.com/select", method=RequestMethod.POST, params="daily")
    public String selectDailyCar(
            @RequestParam(value="daily") String carid,
            @RequestParam(value="userid") String userid,
            @RequestParam(value="startdate") String start,
            @RequestParam(value="enddate") String end,
            Model model) {
        System.out.println("Daily Car is Selected");
        String msg = carid + userid + start + end;
        model.addAttribute("msg", msg);
        return "car_confirm";
    }
        
    @RequestMapping(value="/cst438.carrentals.com/select", method=RequestMethod.POST, params="weekly")
    public String selectWeeklyCar(
            @RequestParam(value="weekly") String carid,
            @RequestParam(value="userid") String userid,
            @RequestParam(value="startdate") String start,
            @RequestParam(value="enddate") String end,
            Model model) {
        System.out.println("Weekly Car is selected");
        String msg = carid + userid + start + end;
        model.addAttribute("msg", msg);
        return "car_confirm";
    }

}
