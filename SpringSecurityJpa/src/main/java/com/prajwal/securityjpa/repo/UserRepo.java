package com.prajwal.securityjpa.repo;

import com.prajwal.securityjpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {


    Optional<User> findByName(String name);
}
