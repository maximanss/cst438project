package cst438.car.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByReserveid(Long id);
    
    List<Reservation> findByCarid(Long id);
    
    List<Reservation> findByReserveidAndUserid(Long rid, Long uid);
    
    @Query(
    	value = "SELECT distinct startdate from Reservation r where r.location=:location", 
    	nativeQuery=true)
    
    ArrayList<Date> findStartDateByLocation(String location);
    
    
    
    @Query(
    	value="SELECT enddate from reservation", nativeQuery=true)
    
    ArrayList<Date> findRentalEndDate(Date date);
    
    
    

}
