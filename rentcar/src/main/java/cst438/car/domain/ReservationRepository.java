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
    

    List<Reservation> findByReserveidAndCompanyid(Long rid, Long cid);


}
