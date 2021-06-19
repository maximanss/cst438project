package cst438.car.controller;


import java.sql.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<ReserveInfo> bookCar(
            @RequestParam(required=true) String companyid, 
            @RequestParam(required=true) String startdate, 
            @RequestParam(required=true) String enddate, 
            @RequestParam(required=true) String location,
            @RequestParam(defaultValue = "any") String cartype){
    	
    	// Log Start
    	System.out.println("bookCar : get /book was called ");
        
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
        
        // Log End
        System.out.println("bookCar : get /book exited. ");
        return carService.bookPartnerReservation(reservation, cartype);
        
    }
    
    @DeleteMapping("/cancel")
    public ResponseEntity<Long> removeBooking(@RequestParam(required = true) String companyid,
            @RequestParam(required = true) String reservationid) {
    	
    	// Log Start
    	System.out.println("removeBooking : delete /cancel was called ");

        // convert the strings to long
        long coid = Long.parseLong(companyid);
        long rid = Long.parseLong(reservationid);
        
        // Log End
        System.out.println("removeBooking : delete /cancel exited. ");
        
        return carService.cancelPartnerReservation(coid, rid);

    }
}