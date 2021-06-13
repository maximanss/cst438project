package cst438.car.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByReserveid(Long id);
    
    List<Reservation> findByCarid(Long id);
    
    List<Reservation> findByReserveidAndUserid(Long rid, Long uid);
    
    @Query(
    	value = "SELECT distinct startdate from Reservation r where r.location=?1", 
    	nativeQuery=true)
    ArrayList<Date> findStartDateByLocation(String location);
    
    
    @Query(
        	value = "SELECT distinct enddate from Reservation r where r.location=?1", 
        	nativeQuery=true)
        ArrayList<Date> findEndDateByLocation(String location);
    
    @Query(
    	value="SELECT totalPrice FROM Reservation r WHERE carID=?1", nativeQuery=true)
    	Float findTotalCostById(int carid);
    
    

}
