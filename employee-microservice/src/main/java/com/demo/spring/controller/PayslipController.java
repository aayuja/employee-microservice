package com.demo.spring.controller;

import java.util.Optional;
import java.util.Set;

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
import com.demo.spring.entity.Payslip;
import com.demo.spring.repository.EmpRepo;
import com.demo.spring.repository.PayslipRepo;

@RestController
@RequestMapping("/pays")
public class PayslipController {

	

	  static Logger logger= LoggerFactory.getLogger(EmpController.class);
	@Autowired
	PayslipRepo paysliprepo;
	
	@Autowired
	EmpRepo emprepo;


	// -----------------------------Employee View payslip---------------------------
	@GetMapping(path = "/emp/{id}/payslip", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findPayslipById(@PathVariable("id") Long id) {
		if (emprepo.existsById(id)) {
			Optional<Employee> empOp = emprepo.findById(id);
			Employee e=empOp.get();
			Set<Payslip> payslips= e.getPayslips();
			logger.info("payslip fetched");
			return ResponseEntity.ok(payslips);
		} else {
			logger.error("payslip not found");
			return ResponseEntity.ok("{\"status\":\"Payslip does not exists\"}");
		}
	}

	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(Exception ex) 
	{
    	 return ResponseEntity.ok(ex.getMessage()) ;
    }

}
