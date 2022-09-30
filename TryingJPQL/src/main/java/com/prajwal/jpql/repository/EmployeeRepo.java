package com.prajwal.jpql.repository;

import com.prajwal.jpql.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {


    //get employee name by id
    @Query("select e.ename from Employee e where e.eid=?1")
    String getNameById(int id);

    //get em[ployees by name
    @Query("from Employee  where ename=?1")
    List<Employee> getEmployeesByName(String name);


    //get employee by designation
    @Query("select e from Employee e where e.designation=:desig")
    List<Employee> getEmployeesByDesignation(@Param("desig") String desig);
}
