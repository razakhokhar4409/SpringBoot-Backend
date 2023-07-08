package com.fullstack.backendjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.backendjwt.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long>  {

}
