package cst438.car.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import cst438.car.service.CarService;
import cst438.car.domain.*;

@Controller
@SessionAttributes({"reservation", "cancellation", "user", "cars"})
public class CarController {
    
    @Autowired
    CarService carService;
    
    @ModelAttribute("reservation")
    public Reservation getNewReservation() {
        return new Reservation();
    }
    
    @ModelAttribute("cancellation")
    public Cancellation getNewCancellation() {
        return new Cancellation();
    }
    
    @ModelAttribute("user")
    public User getNewUser() {
        return new User();
    }
    
    @ModelAttribute("cars")
    public CarListContainer getNewCars() {
        return new CarListContainer();
    }
    
       
    // Home page of URL like: web-hosting-sitename/
    @GetMapping("/")
    public String displayDateLocation(
            Model model) {
        return "date_selection";
    }
    
    @PostMapping("/carchoice")
    public String displayCarChoices(@Valid Reservation reservation,
            BindingResult result,
            CarListContainer cars,
            Model model) {
        
        if (result.hasErrors()) {
            return "date_selection";
        }
        
        if (!carService.validateStartDate(reservation)) {
            System.out.println("Start Date is not 1 day after current date");
            //return "date_selection";
            return "redirect:/?startdate_error";
        }
        if (!carService.validateEndDate(reservation)) {
            System.out.println("End date is not after start date");
            //return "date_selection";
            return "redirect:/?enddate_error";
        }
        //List<Car> carAvailableList = carService.getAvailableCars(reservation);
        cars.setCarlist(carService.getAvailableCars(reservation));
        if (cars.getCarlist().size() == 0) {
            return "redirect:/?empty_error";
        }
        //cars.setCarlist(carAvailableList);
        System.out.println("location:" + reservation.getLocation() +
                " start date:" + reservation.getStartdate() + " end date:" + reservation.getEnddate());
        System.out.println(cars);
        model.addAttribute("cars", cars);
        return "car_choice";
    }
    
    //Request Method mapping for different select button
    @RequestMapping(value="/select", method=RequestMethod.POST, params="selection")
    public String selectCar(
            @Valid Reservation reservation, BindingResult result1,
            @Valid User user, BindingResult result2,
            @RequestParam(value="selection") Long carid,
            Model model) {
        System.out.println("car id:" + carid + " location:" + reservation.getLocation() +
                " start date:" + reservation.getStartdate() + " end date:" + reservation.getEnddate());
        
        if (reservation.getUserid() == 0) {
            String login_msg = "Require User to Log in before selecting any car!";
            System.out.println(login_msg);
            model.addAttribute("login_msg", login_msg);
            return "car_choice";
            
        } 
        // process reservation
        reservation.setCarid(carid);
        Car car = carService.getCarInfo(carid);
        model.addAttribute("car", car);
        carService.setTotalPrice(reservation, car);
        System.out.println(user);
        return "car_confirm";
    }
    
        
    @PostMapping("/confirm")
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
            reservation.setReserveid(0);  // make sure adding reservation not update
            reservation = carService.saveReservation(reservation);
            return "car_reserve";
        } else {
            // start reservation by selecting the dates and location
            return "date_selection";
        }

    }
    
    @PostMapping("/reserve")
    public String displayConfirmation(@Valid Reservation reservation,
            BindingResult result,
            Model model) {
        
        /**
         * make reservation by saving in the db and can the confirmed reservation #
         */
        System.out.println("Reservation is Done!");
        return "date_selection";
    }
    
    // Cancellation page to allow user to cancel car reservation
    @GetMapping("/cancel")
    public String displayCancellation(
            Model model) {
        return "car_cancel";
    }
    
    @PostMapping("/cancel")
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
    
    @PostMapping("/cancel_confirm")
    public String confirmCancellation(Model model) {
        return "date_selection";
    }
    
    @PostMapping("/login")
    public String validateUserInfo(
            @Valid User user, BindingResult result1,
            Reservation reservation,
            Model model) {
        
        String login_result;
        user.setEmailaddress(user.getEmailaddress().toLowerCase());
        User loginUser = carService.getUserInfo(user);
        if (loginUser != null) {
            model.addAttribute("user", loginUser);
            reservation.setUserid(loginUser.getUserid());
            login_result = "Login Successfully!";
        } else {
            login_result = "Login Failed!";
        }
        System.out.println(login_result);
        model.addAttribute("login_result", login_result);
        return "date_selection";
    }
    
    @PostMapping("/register")
    public String displayRegistration(
            @Valid User user, BindingResult result,
            Reservation reservation,
            Model model) {
        
        String register_result;
        if (result.hasErrors()) {
            register_result = "Failed to Create Account - missing/invalid entries!";
        }
        else {
            // check for existing account
            user.setEmailaddress(user.getEmailaddress().toLowerCase());
            User registerUser = carService.getUserInfoByEmail(user.getEmailaddress());
            if (registerUser == null) {
                // new user, save the user info
                user.setUserid(0);  // make sure adding a new record - not updating
                user = carService.saveUser(user);
                reservation.setUserid(user.getUserid());
                register_result = "Account Successfully Created!";
            } else {
                register_result = "Failed to Create - Account Existed!";
            }
        }
        System.out.println(register_result + "-" + user);
        model.addAttribute("register_result", register_result);
        return "date_selection";
    }

}
