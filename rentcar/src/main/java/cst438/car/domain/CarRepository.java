package cst438.car.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByLocation(String placeName);
    
    List<Car> findAll();
    
    List<Car> findByCarid(Long carId);

}

