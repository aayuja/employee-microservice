package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.entity.Payslip;

@Repository
public interface PayslipRepo extends JpaRepository<Payslip,Long> {

}
