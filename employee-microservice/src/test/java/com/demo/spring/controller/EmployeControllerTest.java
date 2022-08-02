package com.demo.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Leave;
import com.demo.spring.entity.Payslip;
import com.demo.spring.entity.Timesheet;
import com.demo.spring.repository.EmpRepo;
import com.demo.spring.repository.LeaveRepo;
import com.demo.spring.repository.PayslipRepo;
import com.demo.spring.repository.TimesheetRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeControllerTest {
	@Autowired
	MockMvc mvc;
	@MockBean
	EmpRepo emprepo;
	@MockBean
	LeaveRepo leaverepo;
	@MockBean
	PayslipRepo paysliprepo;
	@MockBean
	TimesheetRepo timesheetrepo;
	@InjectMocks
	EmpController empcontroller;
	@InjectMocks
	LeaveController LeaveController;
	@InjectMocks
	PayslipController Payslipcontroller;
	@InjectMocks
	TimesheetController Timesheetcontroller;
	final long a=104;
	final long b=106;
	final long c=108;
	

	// ----------------------------------GET----------------------------------
	@Test
	void testFindEmpById() throws Exception {
		Mockito.when(emprepo.findById(a))
				.thenReturn(Optional.of(new Employee(a, "Ayushi", "ayushi@gmail.com", "Lion@123")));

		mvc.perform(get("/empl/emp/104").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$.name").value("Ayushi"));
	}

	@Test
	void testlistAllEmp() throws Exception {
		List<Employee> emplist = new ArrayList<>();
		emplist.add(new Employee(a, "Ayushi", "ayushi@gmail.com", "Lion@123"));
		emplist.add(new Employee( b, "Akansha", "akansha@gmail.com", "Tiger@123"));

		Mockito.when(emprepo.findAll()).thenReturn(emplist);

		mvc.perform(get("/empl/emp").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].name").value("Ayushi"));
	}

	@Test
	void testlistAllLeave() throws Exception {
		List<Leave> leavelist = new ArrayList<>();
		leavelist.add(new Leave(a, "2022-09-20", "2022-09-21", "Sick", "applied"));
		leavelist.add(new Leave( b, "2022-09-22", "2022-09-25", "Travel", "applied"));

		Mockito.when(leaverepo.findAll()).thenReturn(leavelist);

		mvc.perform(get("/leav/emp/leave/list").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].status").value("applied"));
	}
	
	@Test
	void testlistAllTimesheet() throws Exception {
		List<Timesheet> timesheetlist = new ArrayList<>();
		timesheetlist.add(new Timesheet( a, 8, 2, "2022-08-12", "2022-08-19"));
		timesheetlist.add(new Timesheet( b, 8, 2, "2022-08-20", "2022-08-29"));
		Mockito.when(timesheetrepo.findAll()).thenReturn(timesheetlist);

		mvc.perform(get("/timeshe/emp/timesheet/list").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].regularHours").value(8));
	}
	

	@Test
	void testFindLeaveByEmpId() throws Exception {
		Employee e = new Employee( a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Set<Leave> leave = new HashSet<>();
		leave.add(new Leave( b, "2022-09-20", "2022-09-21", "Sick", "applied"));
		leave.add(new Leave( c, "2022-09-20", "2022-09-21", "Travel", "applied"));
        
		e.setLeaves(leave);
		Mockito.when(emprepo.existsById(a)).thenReturn(true);
		Mockito.when(emprepo.findById(a)).thenReturn(Optional.of(e));

		mvc.perform(get("/leav/emp/" + e.getEmpId() + "/leave").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].reasonOfLeave").value("Sick"));
	}

	@Test
	void testFindPayslipByEmpId() throws Exception {
		Employee e = new Employee( a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Set<Payslip> payslip = new HashSet<>();
		payslip.add(new Payslip( b, "november", 22, 4000));
		payslip.add(new Payslip( c, "september", 22, 4000));
		
		e.setPayslips(payslip);
		Mockito.when(emprepo.existsById(a)).thenReturn(true);
		Mockito.when(emprepo.findById(a)).thenReturn(Optional.of(e));

		mvc.perform(get("/pays/emp/" + e.getEmpId() + "/payslip").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].monthOfSalary").value("november"));
	}

	@Test
	void testFindTimesheetByEmpId() throws Exception {
		Employee e = new Employee(a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Set<Timesheet> timesheet = new HashSet<>();
        
		timesheet.add(new Timesheet(b, 8, 2, "2022-08-12", "2022-08-19"));
		timesheet.add(new Timesheet(c, 8, 2, "2022-08-20", "2022-08-29"));
		
		
		e.setTimesheets(timesheet);
		Mockito.when(emprepo.existsById(a)).thenReturn(true);
		Mockito.when(emprepo.findById(a)).thenReturn(Optional.of(e));

		mvc.perform(get("/timeshe/emp/" + e.getEmpId() + "/timesheet").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.[0].regularHours").value(8));
	}

	/*@Test
	void testFindSalaryByEmpId() throws Exception {
		Employee e = new Employee((long) 104, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Salary salary = new Salary((long) 504, 32000, 18000, 5000);
		e.setSalary(salary);
		
		Mockito.when(emprepo.existsById((long) 104)).thenReturn(true);
		Mockito.when(emprepo.findById((long) 104)).thenReturn(Optional.of(e));
		
		mvc.perform(get("/emp/" + e.getEmpId() + "/salary").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].basic").value(32000));
	}
*/
//---------------------------------------------POST------------------------------------------------------------

	@Test
	void testAddEmp() throws Exception {
		Employee e = new Employee(a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");

		when(emprepo.save(e)).thenReturn(e);

		ObjectMapper om = new ObjectMapper();
		String empJson = om.writeValueAsString(e);

		mvc.perform(post("/empl/emp").contentType(MediaType.APPLICATION_JSON).content(empJson)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

//-----------------------------------------PUT-----------------------------------------

	@Test
	void testAddEmpLeave() throws Exception {
		Employee e = new Employee(a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Leave l = new Leave(b, "2022-09-20", "2022-09-21", "Sick", "applied");
		when(emprepo.findById(a)).thenReturn(Optional.of(e));
		when(emprepo.save(e)).thenReturn(e);
		ObjectMapper om = new ObjectMapper();
		String leaveJson = om.writeValueAsString(l);
		// .param("id"+104)

		mvc.perform(
				put("/empl/emp/" + e.getEmpId() + "/addLeave").contentType(MediaType.APPLICATION_JSON).content(leaveJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	void testAddEmpTimesheet() throws Exception {
		Employee e = new Employee(a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Timesheet t = new Timesheet(b, 8, 2, "2022-08-20", "2022-08-29");
		when(emprepo.findById(a)).thenReturn(Optional.of(e));
		when(emprepo.save(e)).thenReturn(e);
		ObjectMapper om = new ObjectMapper();
		String timesheetJson = om.writeValueAsString(t);
        
		mvc.perform(put("/empl/emp/" + e.getEmpId() + "/addTimesheet").contentType(MediaType.APPLICATION_JSON)
				.content(timesheetJson)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	@Test
        public void testAddEmpPayslip() throws Exception {
		Employee e = new Employee(a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Payslip p = new Payslip(b, "november", 22, 4000);
		when(emprepo.findById(a)).thenReturn(Optional.of(e));
		when(emprepo.save(e)).thenReturn(e);
		ObjectMapper om = new ObjectMapper();
		String payslipJson = om.writeValueAsString(p);

		mvc.perform(put("/empl/emp/" + e.getEmpId() + "/addPayslip").contentType(MediaType.APPLICATION_JSON)
				.content(payslipJson)).andExpect(status().isOk())
		        .andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	/*@Test
	public void testAddEmpSalary() throws Exception {
		Employee e = new Employee((long) 104, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Salary s = new Salary((long) 504, 32000, 18000, 5000);
		when(emprepo.findById((long) 104)).thenReturn(Optional.of(e));
		when(emprepo.save(e)).thenReturn(e);
		ObjectMapper om = new ObjectMapper();
		String salaryJson = om.writeValueAsString(s);

		mvc.perform(
				put("/emp/" + e.getEmpId() + "/addSalary").contentType(MediaType.APPLICATION_JSON).content(salaryJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}*/

	@Test
	void testUpdateEmp() throws Exception {
		Employee e = new Employee(a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		when(emprepo.save(e)).thenReturn(e);
		ObjectMapper om = new ObjectMapper();
		String empJson = om.writeValueAsString(e);

		mvc.perform(put("/empl/emp").contentType(MediaType.APPLICATION_JSON).content(empJson)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	

	@Test
	void testUpdateTimesheet() throws Exception {
		Employee e = new Employee(a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Timesheet t = new Timesheet(b, 8, 2, "2022-08-20", "2022-08-29");
		when(emprepo.findById(a)).thenReturn(Optional.of(e));
		when(emprepo.save(e)).thenReturn(e);
		ObjectMapper om = new ObjectMapper();
		String timesheetJson = om.writeValueAsString(t);

		mvc.perform(put("/timeshe/emp/" + e.getEmpId() + "/updateTimesheet").contentType(MediaType.APPLICATION_JSON)
				.content(timesheetJson)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void testUpdateLeaveApprove() throws Exception {
		Employee e = new Employee(a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Leave l = new Leave(b, "2022-09-20", "2022-09-21", "Sick", "applied");
		when(emprepo.findById(a)).thenReturn(Optional.of(e));
		when(emprepo.save(e)).thenReturn(e);
		ObjectMapper om = new ObjectMapper();
		String leaveJson = om.writeValueAsString(l);
		mvc.perform(put("/leav/emp/" + e.getEmpId() + "/updateLeave/approve").contentType(MediaType.APPLICATION_JSON)
				.content(leaveJson)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
	}
	
	
	
	// update leave

	// -------------------------------------------DELETE-------------------------------------------------------------------
	@Test
	void testDeleteLeaveByEmpId() throws Exception {
		Employee e = new Employee(a, "Ayushi Bartwal", "ayushi.bartwal@gmail.com", "Lion@123");
		Leave l = new Leave(b, "2022-09-20", "2022-09-21", "Sick", "applied");
		when(emprepo.existsById(a)).thenReturn(true);
		when(emprepo.findById(a)).thenReturn(Optional.of(e));
		//when(leaverepo.deleteById(b)).thenReturn(true);
		when(emprepo.save(e)).thenReturn(e);

		ObjectMapper om = new ObjectMapper();
		String leaveJson = om.writeValueAsString(l);

		mvc.perform(delete("/empl/emp/" + e.getEmpId() + "/leave").contentType(MediaType.APPLICATION_JSON).content(leaveJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}
	
	/*@DeleteMapping(value = "emp/{id}/leave", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteLeaveByEmpId(@RequestBody Leave l, @PathVariable Long id) {
		if (emprepo.existsById(id)) {
			Optional<Employee> empOp = emprepo.findById(id);
			Employee emp = empOp.get();
			leaverepo.deleteById(l.getLeaveId());
			emp.getLeaves().remove(l);
			emprepo.save(emp);
			return ResponseEntity.ok("{\"status\":\"Leave delete sucessfully\"}");
		} else {

			return ResponseEntity.ok("{\"status\":\"Leave not found....\"}");

		}
	}*/

	
	
}
