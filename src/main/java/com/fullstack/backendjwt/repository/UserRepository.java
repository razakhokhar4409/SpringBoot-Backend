package com.fullstack.backendjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.backendjwt.model.User;


public interface UserRepository  extends JpaRepository<User, Long>{

    User findByUserName(String username);

}
