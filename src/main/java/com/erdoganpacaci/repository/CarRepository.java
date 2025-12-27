package com.erdoganpacaci.repository;

import com.erdoganpacaci.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {


    @Query("SELECT car FROM Car car  Where car.plaka =:plaka")
    Optional<Car> getCarsByPlaka(@Param("plaka") String plaka);

}
