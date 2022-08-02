package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.spring.entity.Salary;

@Repository
public interface SalaryRepo extends JpaRepository<Salary,Long> {

}
