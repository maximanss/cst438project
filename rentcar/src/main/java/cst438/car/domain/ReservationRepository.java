package cst438.car.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByReserveid(Long id);
    
    List<Reservation> findByCarid(Long id);
    
    List<Reservation> findByReserveidAndUserid(Long rid, Long uid);

}
