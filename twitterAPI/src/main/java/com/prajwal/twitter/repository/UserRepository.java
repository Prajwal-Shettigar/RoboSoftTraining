package com.prajwal.twitter.repository;

import com.prajwal.twitter.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,String> {
}
