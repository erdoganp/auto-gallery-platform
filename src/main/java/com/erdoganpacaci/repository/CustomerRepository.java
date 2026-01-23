package com.erdoganpacaci.repository;

import com.erdoganpacaci.model.Customer;
import com.erdoganpacaci.model.GalleristCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT a FROM Customer a LEFT JOIN FETCH a.address WHERE a.address.id = :id")
    Optional<Customer> findWithAddress(@Param("id") Long id);

    @Query("SELECT a FROM Customer a LEFT JOIN FETCH a.account WHERE a.account.id = :id")
    Optional<Customer> findWithAccount(@Param("id") Long id);


}
