package com.prajwal.userService.model;

import com.prajwal.userService.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class UserDepoResponse {
    User user;
    Department department;
}
