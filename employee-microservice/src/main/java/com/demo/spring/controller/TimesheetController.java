package com.demo.spring.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Timesheet;
import com.demo.spring.repository.EmpRepo;
import com.demo.spring.repository.TimesheetRepo;

@RestController
@RequestMapping("/timeshe")
public class TimesheetController {
	static Logger logger= LoggerFactory.getLogger(EmpController.class);

	@Autowired
	TimesheetRepo timesheetrepo;
	
	@Autowired
	EmpRepo emprepo;


	//-----------------------------Admin View timesheet---------------------------
	
	@GetMapping(path = "/emp/timesheet/list",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Timesheet>> listAllTimesheets(){
		return ResponseEntity.ok(timesheetrepo.findAll());
	}
	//-----------------------------employee view timesheet-----------------------
	@GetMapping(path="/emp/{id}/timesheet",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findTimesheetByEmpId(@PathVariable("id") Long id) {
		if (emprepo.existsById(id)) {
			Optional<Employee> empOp = emprepo.findById(id);
			Employee e=empOp.get();
			Set<Timesheet> timesheets= e.getTimesheets();
			logger.info("timesheet fetched");
			return ResponseEntity.ok(timesheets);
		} else {
			logger.error("timesheet not found");
			return ResponseEntity.ok("{\"status\":\"Timesheet does not exist\"}");
		}
	}
   
	//-------------------------------admin Update timesheet--------------------------
	
	@PutMapping(value = "/emp/{id}/updateTimesheet", produces =MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateEmpTimesheet(@PathVariable Long id, @RequestBody Timesheet timesheet) {
		Optional<Employee> empOp = emprepo.findById(id);
		Employee emp = empOp.get();
		emp.getTimesheets().add(timesheet);
		emprepo.save(emp);
		logger.info("timesheet saved");
		return ResponseEntity.ok("{\"status\":\"Timesheet saved ! \"}");

	}
   
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(Exception ex) 
	{
    	 return ResponseEntity.ok(ex.getMessage()) ;
    }
	
	
}
