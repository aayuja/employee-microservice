package com.demo.spring.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/*
@Builder/y this 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
*/
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

	@Id
	@SequenceGenerator(name = "employee_seq", sequenceName = "employee_sequence",
	initialValue = 1, allocationSize = 2)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeea_seq")
	@Column(name = "EMPID", nullable = false)
	private Long empId;
	private String name;
	private String email;
	private String password;

	public Employee() {

	}

	@OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "EMPID")
	Set<Leave> leaves = new HashSet<>();


	@OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "EMPID")
	Set<Timesheet> timesheets = new HashSet<>();


	@OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "EMPID")
	Set<Payslip> payslips = new HashSet<>();


	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "EMPID")
	private Salary salary;

	public Employee(Long empId, String name, String email,String password) {
		this.empId = empId;
		this.name = name;
		this.email = email;
		this.password=password;

	}

	
	public Long getEmpId() {
		return empId;
	}


	public void setEmpId(Long empId) {
		this.empId = empId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Set<Leave> getLeaves() {
		return leaves;
	}


	public void setLeaves(Set<Leave> leaves) {
		this.leaves = leaves;
	}


	public Set<Timesheet> getTimesheets() {
		return timesheets;
	}


	public void setTimesheets(Set<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}


	public Set<Payslip> getPayslips() {
		return payslips;
	}


	public void setPayslips(Set<Payslip> payslips) {
		this.payslips = payslips;
	}


	public Salary getSalary() {
		return salary;
	}


	public void setSalary(Salary salary) {
		this.salary = salary;
	}


	@Override
	public int hashCode() {
		return Objects.hash(empId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return empId == other.empId;
	}

	/*@Override
	public String toString() {
		return "Employee [empId=" + empId + ", name=" + name + ", email=" + email + "]";
	}*/

}
