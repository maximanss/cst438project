package cst438.car.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    
    public List<Car> getAvailableCars(Reservation reservation) {
            
        List<Car> cars = carRepository.findByLocation(reservation.getLocation());
        for (Car car: cars )
        {
            // check the same car in reservation db to see the request dates are good 
            List<Reservation> reservedList = reservationRepository.findByCarid(car.getCarid());
            for (Reservation reserved: reservedList) {
                if (!(reservation.getEnddate().before(reserved.getStartdate()) ||
                        reservation.getStartdate().after(reserved.getEnddate()))) {
                    // days overlapped, remove from the available car list, continue with next car in cars
                    cars.remove(car);
                    break;
                }
            }
        }
        return cars;
            
    }
    
    public Car getCarInfo(Long carId) {
        List<Car> cars = carRepository.findByCarid(carId);
        if (cars.size() > 0) {
            return cars.get(0);
        } else {
            return new Car();
        }
    }
    
    public User getUserInfo(Long userId) {
        List<User> users = userRepository.findByUserid(userId);
        return users.get(0);
    }
    
    public void setTotalPrice(Reservation reservation, Car car) {
        Long numOfDays = ChronoUnit.DAYS.between(
                reservation.getStartdate().toLocalDate(), 
                reservation.getEnddate().toLocalDate());
        System.out.println("Number of Rental Days:"+ numOfDays);
        Long numOfWeeks = numOfDays / 7;
        Long numOfDaysLeft = numOfDays % 7;
        float total = numOfWeeks * car.getWeeklyprice() + numOfDays * car.getDailyprice();
        reservation.setTotalprice(total);
    }
    
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
