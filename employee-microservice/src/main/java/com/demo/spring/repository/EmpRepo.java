package com.demo.spring.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Leave;
import com.demo.spring.entity.Payslip;
import com.demo.spring.entity.Salary;
import com.demo.spring.entity.Timesheet;

@Repository
public interface EmpRepo extends JpaRepository<Employee, Long> {

public Optional<Employee> findByName(String name);
	

}
