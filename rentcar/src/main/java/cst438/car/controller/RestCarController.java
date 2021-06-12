package cst438.car.controller;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ArrayList<Date> getEndDate(Date date){
		ArrayList<Date> dates = carService.getRentalEndDate(date);
		System.out.println("End dates for all reservations: " + dates);
		return dates;
	}
	
	

}
