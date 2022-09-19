package com.prajwal.productjpa.repository;

import com.prajwal.productjpa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findByName(String name);

    List<Employee> findByCity(String city);

    @Transactional
    @Modifying
    @Query(value = "delete from employee e where e.city=?1",nativeQuery = true)
    void deleteByCity(String city);

    List<Employee> findByNameandCity(String name,String city);

}
