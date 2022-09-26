package com.prajwal.manytomany.repository;

import com.prajwal.manytomany.entity.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerRepository extends JpaRepository<Computer,Integer> {
}
