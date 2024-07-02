package com.jsp.ecommerce_application.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce_application.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
