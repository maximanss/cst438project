package cst438.car.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/getStartDates")
	public ArrayList<Date> getStartDate(@RequestParam("location") String location){
		ArrayList<Date> dates = carService.getStartDateByLocation(location);
		return dates;
	}
	
	@GetMapping("/getEndDates")
	public ArrayList<Date> getEndDate(@RequestParam("location") String location){
		ArrayList<Date> dates = carService.getEndDateByLocation(location);
		return dates;
	}
	
	@GetMapping("/getTotalCost/")
	public Float getTotalCostById(@RequestParam("carid") int carid) {
		Float totalCost = carService.getTotalCost(carid);
		return totalCost;
	}
	
	@GetMapping("/getAvailableCars")
	public List<Car> getAvailableCarsByLocation(@RequestParam("startdate") String startdate, 
			@RequestParam("enddate") String enddate, @RequestParam("location") String location){
		// Convert the string to Date
		Date date1=Date.valueOf(startdate);
		Date date2=Date.valueOf(enddate);
		Reservation reservation = new Reservation();
		reservation.setLocation(location);
		reservation.setStartdate(date1); // Do you have the start date?
		reservation.setEnddate(date2);  // We need the dates to look for available cars, becau
		List<Car> availableCars = carService.getAvailableCars(reservation);
		return availableCars;
	}
	
	

}
