package com.prajwal.onetomany.repository;

import com.prajwal.onetomany.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
