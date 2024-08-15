package com.liviabraz.eclipseHotel.repository;

import com.liviabraz.eclipseHotel.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
