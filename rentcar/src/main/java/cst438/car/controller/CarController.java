package cst438.car.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import cst438.car.service.CarService;
import cst438.car.domain.*;

@Controller
@SessionAttributes({"reservation", "cancellation"})
public class CarController {
    
    @Autowired
    CarService carService;
    
    @ModelAttribute
    public Reservation getNewReservation() {
        return new Reservation();
    }
    
    @ModelAttribute
    public Cancellation getNewCancellation() {
        return new Cancellation();
    }
    
    // Home page of URL like: web-hosting-sitename/cst438.carrentals.com
    @GetMapping("/cst438.carrentals.com")
    public String displayDateLocation(Model model) {
        return "date_selection";
    }
    
    @PostMapping("/cst438.carrentals.com/carchoice")
    public String displayCarChoices(@Valid Reservation reservation,
            BindingResult result,
            Model model) {
        if (!reservation.getEnddate().after(reservation.getStartdate())) {
            System.out.println("End date is not after start date");
            return "date_selection";
        }
        Iterable<Car> cars = carService.getAvailableCars(reservation);
        model.addAttribute("cars", cars);
        System.out.println("location:" + reservation.getLocation() +
                " start date:" + reservation.getStartdate() + " end date:" + reservation.getEnddate());

        return "car_choice";
    }
    
    //Request Method mapping for different select button
    @RequestMapping(value="/cst438.carrentals.com/select", method=RequestMethod.POST, params="selection")
    public String selectDailyCar(@Valid Reservation reservation,
            BindingResult result,
            @RequestParam(value="selection") Long carid,
            Model model) {
        System.out.println("Daily Car is Selected, with car id:" + carid);
        System.out.println("location:" + reservation.getLocation() +
                " start date:" + reservation.getStartdate() + " end date:" + reservation.getEnddate());
        
        reservation.setCarid(carid);
        if (reservation.getUserid() == 0) {
            System.out.println("Require User to Log in");
            
            //following are temporary, remove them when log in is ready
            User tempUser = carService.getUserInfoByEmail("bob@gmail.com");
            reservation.setUserid(tempUser.getUserid());
            
        } 
        
        User user = carService.getUserInfo(reservation.getUserid());
        model.addAttribute("user", user);
        Car car = carService.getCarInfo(carid);
        model.addAttribute("car", car);
        carService.setTotalPrice(reservation, car);
        return "car_confirm";
    }
    
        
    @PostMapping("/cst438.carrentals.com/confirm")
    public String displayConfirmation(@Valid Reservation reservation,
            BindingResult result,
            @RequestParam("confirm") String confirm,
            Model model) {
        
        /**
         * make reservation by saving in the db and can the confirmed reservation #
         */
        System.out.println("Confirmation:" + confirm);
        if (confirm.equals("yes")) {
            System.out.println("Confirmed!");
            reservation = carService.saveReservation(reservation);
            return "car_reserve";
        } else {
            // start reservation by selecting the dates and location
            return "date_selection";
        }

    }
    
    @PostMapping("/cst438.carrentals.com/reserve")
    public String displayConfirmation(@Valid Reservation reservation,
            BindingResult result,
            Model model) {
        
        /**
         * make reservation by saving in the db and can the confirmed reservation #
         */
        System.out.println("Reservation is Done!");
        Long id = reservation.getReserveid();
        // reuse the same reservation but with different reserve id
        reservation.setReserveid(id + 1);
        return "date_selection";
    }
    
    // Cancellation page to allow user to cancel car reservation
    @GetMapping("/cst438.carrentals.com/cancel")
    public String displayCancellation(Model model) {
        return "car_cancel";
    }
    
    @PostMapping("/cst438.carrentals.com/cancel")
    public String processCancellation(@Valid Cancellation cancellation, 
            BindingResult result,
            @RequestParam("confirm") String confirm,
            Model model) {
        System.out.println("Email Address:"+ cancellation.getEmailAddress() + 
                " Reservation Number:" + cancellation.getReserveNum());
        if (!confirm.equals("yes")) {
            System.out.println("Aborted!");
            return "date_selection";
        } else {
            if (result.hasErrors()) {
                return "car_cancel";
            }
            if (carService.cancelUserReservation(cancellation.getEmailAddress(), 
                    cancellation.getReserveNum())) {
                return "cancel_confirm";
            } else {
                String error_msg = "Reservation Not Found!";
                System.out.println(error_msg);
                model.addAttribute("error", error_msg);
                return "car_cancel";
            }
        }
    }
    
    @PostMapping("/cst438.carrentals.com/cancel_confirm")
    public String confirmCancellation(Model model) {
        return "date_selection";
    }

}
