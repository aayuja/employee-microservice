package com.demo.spring.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Leave;
import com.demo.spring.repository.EmpRepo;
import com.demo.spring.repository.LeaveRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/leav")
public class LeaveController {


	  static Logger logger= LoggerFactory.getLogger(EmpController.class);
	@Autowired
	LeaveRepo leaverepo;

	@Autowired
	EmpRepo emprepo;

	// -------------------------------Admin view list of leaves----------------------------------
	@GetMapping(path = "/emp/leave/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Leave>> listAllLeave() {
		 logger.info("leave list fetched");
		return ResponseEntity.ok(leaverepo.findAll());
	}

	// ----------------------------------employee View approved leaves--------------------
	@GetMapping(path = "/emp/{id}/leave", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findLeaveByEmpId(@PathVariable("id") Long id) {
		if (emprepo.existsById(id)) {
			Optional<Employee> empOp = emprepo.findById(id);
			Set<Leave> leaves = empOp.get().getLeaves();
			logger.info("leave  fetched");
			return ResponseEntity.ok(leaves);
		} else {
			logger.error("Leave not found");
			return ResponseEntity.ok("Leave does not exist");
		}
	}

	// -------------------------------Admin Approve leave--------------------------

	@PutMapping(value = "/emp/{id}/updateLeave/approve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateLeaveApprove(@RequestBody Leave l ,@PathVariable Long id ) {
		Optional<Employee> empOp = emprepo.findById(id);
		Employee emp = empOp.get();
		l.setStatus("approve");
		emp.getLeaves().add(l);
		emprepo.save(emp);
		logger.info("leave approved");
		return ResponseEntity.ok("{\"status\":\"Leave Updated\"}");
	}

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleError(Exception ex) {
		return ResponseEntity.ok(ex.getMessage());
	}
}
