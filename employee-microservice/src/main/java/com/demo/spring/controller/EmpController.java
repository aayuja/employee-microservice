package com.demo.spring.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Leave;
import com.demo.spring.entity.Payslip;
import com.demo.spring.entity.Salary;
import com.demo.spring.entity.Timesheet;
import com.demo.spring.repository.EmpRepo;
import com.demo.spring.repository.LeaveRepo;
import com.demo.spring.repository.PayslipRepo;
import com.demo.spring.repository.SalaryRepo;
import com.demo.spring.repository.TimesheetRepo;

import io.micrometer.core.annotation.Timed;


@RestController
@RequestMapping("/empl")
public class EmpController {

	  static Logger logger= LoggerFactory.getLogger(EmpController.class);
	@Autowired
	EmpRepo emprepo;

	@Autowired
	LeaveRepo leaverepo;

	@Autowired
	PayslipRepo paysliprepo;

	@Autowired
	SalaryRepo salaryrepo;

	@Autowired
	TimesheetRepo timesheetrepo;

	// -----------------------------Employee View Profile ---------------------------
	
	@GetMapping(path = "/emp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="emp_app")
	public ResponseEntity findEmpById(@PathVariable("id") Long id) {
		Optional<Employee> empOp = emprepo.findById(id);
		if (empOp.isPresent()) {
			  logger.info("Employee profile found");
			return ResponseEntity.ok(empOp.get());
		} else {
			logger.error("Employee not found");
			return ResponseEntity.status(404).body("{\"status\":\"Emp Not Found\"}");
		}
	}

	// --------------------------------Admin View List of employee------------------------------
	@GetMapping(path = "/emp")
	public ResponseEntity<List<Employee>> listAll() {
		logger.info("Employee list found");
		return ResponseEntity.ok(emprepo.findAll());

	}

	// ------------------------------Admin Add employee-----------------------------
	@PostMapping(value = "/emp",  produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addEmp(@RequestBody Employee e) {
		emprepo.save(e);
		logger.info("Employee added");
		return ResponseEntity.ok("{\"status\":\"Emp saved..\"}");
	}
	// ------------------------------Employ apply leaves-------------------------------
	@PutMapping(value = "/emp/{id}/addLeave", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addEmpLeave(@PathVariable Long id, @RequestBody Leave leave) {
		Optional<Employee> empOp = emprepo.findById(id);
		Employee emp = empOp.get();
		emp.getLeaves().add(leave);
		emprepo.save(emp);
		logger.info("Leave added");
		return ResponseEntity.ok("{\"status\":\"Leave saved..\"}");

	}

	// ------------------------------Admin Add payslip-------------------------------

	@PutMapping(value = "/emp/{id}/addPayslip", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addEmpPayslip(@PathVariable Long id, @RequestBody Payslip payslip) {
		Optional<Employee> empOp = emprepo.findById(id);
		Employee emp = empOp.get();
		emp.getPayslips().add(payslip);
		emprepo.save(emp);
		logger.info("payslip added");
		return ResponseEntity.ok("{\"status\":\"Payslip saved..\"}");
	}


	// -------------------------------Admin Add timesheet------------------------------
	//-------------------------------Employee Add timesheet---------------------------

	@PutMapping(value = "/emp/{id}/addTimesheet", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addEmpTimesheet(@PathVariable Long id, @RequestBody Timesheet timesheet) {
		Optional<Employee> empOp = emprepo.findById(id);
		Employee emp = empOp.get();
		emp.getTimesheets().add(timesheet);
		emprepo.save(emp);
		logger.info("Timesheet added");
		return ResponseEntity.ok("{\"status\":\"Timesheet saved ! \"}");
	}

	// ------------------------------Admin Add salary-------------------------------

	@PutMapping(value = "/emp/{id}/addSalary", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addEmpSalary(@PathVariable Long id, @RequestBody Salary salary) {

		Optional<Employee> empOp = emprepo.findById(id);
		Employee emp = empOp.get();
		salary.setEmployee(emp);
		salaryrepo.save(salary);
		logger.info("salary added");
		return ResponseEntity.ok("{\"status\":\"Salary saved ! \"}");

	}

	// -------------------------------Employee Edit profile--------------------------

	@PutMapping(value = "/emp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateEmp(@RequestBody Employee e) {
		if (emprepo.existsById(e.getEmpId())) {
			emprepo.save(e);
			logger.info("Employee edited");
			return ResponseEntity.ok("{\"status\":\"Emp Updated\"}");
		} else {
			emprepo.save(e);
			logger.info("Employee added");
			return ResponseEntity.ok("{\"status\":\"Emp added....\"}");

		}
	}
	

	// ----------------------------Admin delete rejected leave----------------------------

			@DeleteMapping(value = "emp/{id}/leave", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<String> deleteLeaveByEmpId(@RequestBody Leave l, @PathVariable Long id) {
				if (emprepo.existsById(id)) {
					Optional<Employee> empOp = emprepo.findById(id);
					Employee emp = empOp.get();
					leaverepo.deleteById(l.getLeaveId());
					emp.getLeaves().remove(l);
					emprepo.save(emp);
					logger.info("admin deleted rejected leaves");
					return ResponseEntity.ok("{\"status\":\"Leave delete sucessfully\"}");
				} else {
					logger.error("leaves not found");
					return ResponseEntity.ok("{\"status\":\"Leave not found....\"}");

				}
			}
			
			 //----------------employee login-----------------------------
			/*@GetMapping(path = "/emp/{name}/name", produces = MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity checkEmp(@PathVariable("name") String name) {
				Optional<Employee> e = emprepo.findByName(name);
					return ResponseEntity.ok(e.get());
				}
			//----------------employee change password------------------------------
			@PutMapping(path = "/emp/{name}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity changePass(@PathVariable("name") String name,@PathVariable("password") String password) {
				Optional<Employee> empOp = emprepo.findByName(name);
					Employee e=empOp.get();
					e.setPassword(password);
					emprepo.save(e);
					return ResponseEntity.ok("{\"status\":\"Password changed...\"}");

				}*/
			
			
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleError(Exception ex) {
		return ResponseEntity.ok(ex.getMessage());
	}

}
