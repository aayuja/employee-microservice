package com.demo.spring.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Salary;
import com.demo.spring.repository.EmpRepo;
import com.demo.spring.repository.SalaryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/sala")
public class SalaryController {
	static Logger logger= LoggerFactory.getLogger(EmpController.class);
	@Autowired
	SalaryRepo salaryrepo;
	
	@Autowired
	EmpRepo emprepo;
	

	
	//-------------------------Employe view salary----------------------
	@GetMapping(path="/emp/{id}/salary",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findSalaryByEmpId(@PathVariable("id") Long id) {
		if (emprepo.existsById(id)) {
			Optional<Employee> empOp = emprepo.findById(id);
			Employee e=empOp.get();
			Salary s=e.getSalary();
			logger.info("salary fetched");
			return ResponseEntity.ok(s);
		} else {
			logger.error("salary not found");
			return ResponseEntity.ok("{\"status\":\"Salary does not exist\"}");
		}
	}
	
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(Exception ex) 
	{
    	 return ResponseEntity.ok(ex.getMessage()) ;
    }

}
