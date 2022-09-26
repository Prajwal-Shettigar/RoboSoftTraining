package com.prajwal.onetoone.repository;

import com.prajwal.onetoone.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
