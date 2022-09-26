package com.prajwal.onetoone.repository;

import com.prajwal.onetoone.entity.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo,Integer> {
}
