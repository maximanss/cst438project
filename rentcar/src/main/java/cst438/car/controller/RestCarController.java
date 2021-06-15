package cst438.car.controller;


import java.sql.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cst438.car.domain.*;
import cst438.car.service.CarService;


@RestController
@RequestMapping("/api")
public class RestCarController {
    
    @Autowired
    private CarService carService;
    
        
    @GetMapping("/book")
    public ResponseEntity<ReserveInfo> bookCar(@RequestParam("companyid") String companyid, 
            @RequestParam("startdate") String startdate, 
            @RequestParam("enddate") String enddate, 
            @RequestParam("location") String location,
            @RequestParam(defaultValue = "Standard") String cartype){
        
        long company = Long.parseLong(companyid); // convert companyid back to long
        
        // Convert the string to Date
        Date date1=Date.valueOf(startdate);
        Date date2=Date.valueOf(enddate);
        
        // prepare the reservation detail to make a reservation
        Reservation reservation = new Reservation();
        reservation.setLocation(location);
        reservation.setStartdate(date1); 
        reservation.setEnddate(date2);
        reservation.setCompanyid(company);
         
        return carService.bookPartnerReservation(reservation, cartype);
        
    }
    
    

}