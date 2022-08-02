package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.entity.Leave;

@Repository
public interface LeaveRepo extends JpaRepository<Leave, Long> {

}
