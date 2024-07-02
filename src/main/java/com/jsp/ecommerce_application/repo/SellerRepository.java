package com.jsp.ecommerce_application.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce_application.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

}
