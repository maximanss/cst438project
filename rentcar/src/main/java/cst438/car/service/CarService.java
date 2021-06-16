package cst438.car.service;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
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
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private PartnerRepository partnerRepository;
    
    // this constructor is used in unit test to stub out
    // carRepository, userRepository and reservationRepository.
    public CarService(CarRepository mockCarRepository, 
            UserRepository mockUserRepository, 
            ReservationRepository mockReservationRepository) {
        
        this.carRepository = mockCarRepository;
        this.userRepository = mockUserRepository;
        this.reservationRepository = mockReservationRepository;
    }
    
    // return true if start date is after the current date
    public boolean validateStartDate(Reservation reservation) {
        long millis = System.currentTimeMillis();
        Date currentDate = new java.sql.Date(millis);
        if (reservation.getStartdate() == null)
            return false;
        if (reservation.getStartdate().after(currentDate)) {
            return true;
        } else {
            return false;
        }
    }
    
    // return true if end date is after the start date
    public boolean validateEndDate(Reservation reservation) {
        if (reservation.getEnddate() == null)
            return false;
        if (reservation.getEnddate().after(reservation.getStartdate())) {
            return true;
        } else {
            return false;
        }
    }
    
    public List<Car> getAvailableCars(Reservation reservation) {
            
        List<Car> cars = carRepository.findByLocationIgnoreCase(reservation.getLocation());
        List<Car> availableCars = new ArrayList<Car>(); 
        for (Car car: cars )
        {
            boolean overlap = false;
            //System.out.println("Car:"+car);
            // check the same car in reservation db to see the request dates are good 
            List<Reservation> reservedList = reservationRepository.findByCarid(car.getCarid());
            for (Reservation reserved: reservedList) {
                if (!(reservation.getEnddate().before(reserved.getStartdate()) ||
                        reservation.getStartdate().after(reserved.getEnddate()))) {
                    // days overlapped, this car cannot be in the list, continue with next car in cars
                    overlap = true;
                    break;
                }
            }
            if (!overlap) {
                // no overlapping days, add the car to the available car list
                availableCars.add(car);
            }
        }
        return availableCars;
            
    }
    
    // return null if no cars found with that id
    public Car getCarInfo(Long carId) {
        List<Car> cars = carRepository.findByCarid(carId);
        if (cars.size() > 0) {
            return cars.get(0);
        } else {
            return null;
        }
    }
    
    // return null if no users found with that id
    public User getUserInfo(User user) {
        List<User> users = userRepository.findByEmailaddressAndPassword(
                user.getEmailaddress(), user.getPassword());
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
    
    // return null if no users found with that id
    public User getUserInfo(Long userId) {
        List<User> users = userRepository.findByUserid(userId);
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
    
    // return null if no users found with the email
    public User getUserInfoByEmail(String email) {
        List<User> users = userRepository.findByEmailaddress(email);
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
    
    // create a new user in the repository
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    
    public void setTotalPrice(Reservation reservation, Car car) {
        Long numOfDays = ChronoUnit.DAYS.between(
                reservation.getStartdate().toLocalDate(), 
                reservation.getEnddate().toLocalDate());
        System.out.println("Number of Rental Days:"+ numOfDays);
        
        if (numOfDays > 0) {
            // Calculate the total cost based on number of rental days
            Long numOfWeeks = numOfDays / 7;
            Long numOfDaysLeft = numOfDays % 7;
            float total = numOfWeeks * car.getWeeklyprice() + numOfDaysLeft * car.getDailyprice();
            reservation.setTotalprice(total);
        } else {
            reservation.setTotalprice(0);
        }
    }
    
   
    
    // create a new reservation in the repository
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
    
    public boolean cancelUserReservation(String email, Long reserveNum) {
        // check whether the email address is in user table
        List<User> users = userRepository.findByEmailaddress(email);
        if (users.size() > 0) 
        {   // found the user, then look for the reservation
            Long userid = users.get(0).getUserid();
            
            // Check whether the reservation exists and belongs to the user (email address)
            List<Reservation> reservations = reservationRepository.findByReserveidAndUserid(reserveNum, userid);
            if (reservations.size() > 0) {
                // delete the reservation
                reservationRepository.deleteById(reserveNum);
                return true;
            }
        }
        return false; // fail since the reservation not exist
    }

    
    public boolean validateCompanyId(Long companyid) {
        List<Partner> companylist = partnerRepository.findByCompanyid(companyid);
        if (companylist.size() == 0) {
            return false;
        }
        return true;
    }
    
    public ResponseEntity<ReserveInfo> bookPartnerReservation(Reservation reservation, String cartype) {
        if (validateCompanyId(reservation.getCompanyid())) {
            
            // get all the available cars that are good with the location and dates
            List<Car> cars = getAvailableCars(reservation);
            
            System.out.println("bookPartnerReservation- cartype:" + cartype);
            System.out.println("bookPartnerReservation:" + cars);
            
            if (cars.size() > 0) {
               List<Car> matchedCars = new ArrayList<Car>();
               
               if (!cartype.equals("any")) {
                   // filter the list with desired cartype
                   for (Car car : cars) {
                           if (car.getCartype().getCartypename().equalsIgnoreCase(cartype) ) {
                               matchedCars.add(car);
                           }
                       } 
               } else {
                   // no cartype is not specified, so copy the whole list
                   for (Car car: cars) {
                       matchedCars.add(car);
                   }
               }
               
               if (matchedCars.size() > 0 )
               {
                   // Take the first car in the matchedCars list to reserve it for the partner company
                   Car car = matchedCars.get(0);
                   reservation.setCarid(car.getCarid());
                   
                   // determine the total price
                   // give them discount in next phase of project development
                   setTotalPrice(reservation, car);
                   
                   // save the reservation to db
                   reservation.setReserveid(0); // make sure to set it to zero for saving
                   Reservation reserve = saveReservation(reservation);
                   
                   System.out.println("bookPartnerReservation" + reserve);
                   
                   ReserveInfo reserveInfo = new ReserveInfo(reserve.getReserveid(),
                           car.getCartype().getCartypename(),
                           car.getDescription(),
                           reserve.getTotalprice());
                   System.out.println("bookPartnerReservation" + reserveInfo);
                   
                   return new ResponseEntity<ReserveInfo>(reserveInfo, HttpStatus.OK);
                   
               }
            }
            
            // acknowledge the request but no cars available for the request
            System.out.println("bookPartnerReservation, no cars available for the request");
            return new ResponseEntity<ReserveInfo>(HttpStatus.NO_CONTENT);
            
        } else {
            // company id not found. Send 404 return code.
            // return new ReserveInfo(HttpStatus.NOT_FOUND);
            System.out.println("bookPartnerReservation, company id not matched any in the database");
            return new ResponseEntity<ReserveInfo>(HttpStatus.NOT_FOUND);
        }
    }

    
    public ResponseEntity<Long> cancelPartnerReservation(Long partnerid, Long reserveNum) {
        
        // check whether the partner company is in the db
        if (validateCompanyId(partnerid)) {
            
            // the company is a partner company
            
            // Check whether the reservation exists and belongs to the user (email address)
            List<Reservation> reservations = reservationRepository.findByReserveidAndCompanyid(reserveNum, partnerid);
            if (reservations.size() > 0) {
                // delete the reservation since the reservation belongs to that company
                reservationRepository.deleteById(reserveNum);
                
            }
            return new ResponseEntity<>(reserveNum, HttpStatus.OK);
            // return true even the reservation is no longer in the db
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);// not found since the company does not exist
    }

}

