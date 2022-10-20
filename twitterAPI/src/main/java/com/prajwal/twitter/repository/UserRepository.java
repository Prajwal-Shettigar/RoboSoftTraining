package com.prajwal.twitter.repository;

import com.prajwal.twitter.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    List<User> findAllByOrderByFollowersCountDesc(Pageable pageable);
}
