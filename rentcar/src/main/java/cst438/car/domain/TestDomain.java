package cst438.car.domain;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TestDomain {

    @Autowired
    CarRepository carRepository;
    
    
    
    

    /**
     * Response to user request like URL localhost:8080/cars
     * 
     */
    @GetMapping("/cars/{cityName}")   
    public String displayAllCars(@PathVariable("cityName") String location, Model model) {
        Iterable<Car> cars = carRepository.findByLocation(location);
        for (Car car : cars) {
            System.out.println("ID:"+car.getCarid()+", Type:"+ car.getCartype().getCartypename());
        }
        model.addAttribute("cars", cars);
        return "car_list";
    }
    
    @Autowired
    CarTypeRepository carTypeRepository;
    
    @GetMapping("/cartype/{id}")
    public String displayTypeName(@PathVariable("id") String ID, Model model) {
        Integer id = Integer.valueOf(ID);
        Iterable<CarType> carTypes = carTypeRepository.findByCartypeid(id);
        model.addAttribute("cartypes", carTypes);
        return "car_type";
    }
    
    @Autowired
    PartnerRepository partnerRepository;
    
    @GetMapping("/partner/{id}")
    public String displayPartner(@PathVariable("id") String ID, Model model) {
        Long id = Long.valueOf(ID);
        Iterable<Partner> companies = partnerRepository.findByCompanyid(id);
        model.addAttribute("companies", companies);
        return "partner";
    }
    
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/user/{id}")
    public String displayUser(@PathVariable("id") String ID, Model model) {
        Long id = Long.valueOf(ID);
        Iterable<User> persons = userRepository.findByUserid(id);
        model.addAttribute("persons", persons);
        return "user";
    }
    
    @Autowired
    ReservationRepository reservationRepository;
    
    @GetMapping("/reservation/{id}")
    public String displayReservation(@PathVariable("id") String ID, Model model) {
        Long id = Long.valueOf(ID);
        Iterable<Reservation> bookings = reservationRepository.findByReserveid(id);
        model.addAttribute("bookings", bookings);
        return "reservation";
    }
    
}