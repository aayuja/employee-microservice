package com.demo.spring.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/*
@Builder/y this 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
*/
@Entity
@Table(name = "PAYSLIP")
public class Payslip {
	
	@Id
	@SequenceGenerator(name="payslip_seq",
	sequenceName="payslip_sequence", initialValue =100,allocationSize=2)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="payslip_seq")
	@Column(name="PAYSLIPID",nullable=false)
	private Long payslipId;
	private String monthOfSalary;
	private int totalPaidDays;
	private int totalSalary;

	public Payslip() {

	}

	@ManyToOne
	@JoinColumn(name = "EMPID")
	@JsonIgnore
	private Employee employee;

	
	
	public Payslip(Long payslipId, String monthOfSalary, int totalPaidDays, int totalSalary) {
		this.payslipId = payslipId;
		this.monthOfSalary = monthOfSalary;
		this.totalPaidDays = totalPaidDays;
		this.totalSalary = totalSalary;
	}


	public Long getPayslipId() {
		return payslipId;
	}


	public void setPayslipId(Long payslipId) {
		this.payslipId = payslipId;
	}


	public String getMonthOfSalary() {
		return monthOfSalary;
	}


	public void setMonthOfSalary(String monthOfSalary) {
		this.monthOfSalary = monthOfSalary;
	}


	public int getTotalPaidDays() {
		return totalPaidDays;
	}


	public void setTotalPaidDays(int totalPaidDays) {
		this.totalPaidDays = totalPaidDays;
	}


	public int getTotalSalary() {
		return totalSalary;
	}


	public void setTotalSalary(int totalSalary) {
		this.totalSalary = totalSalary;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	@Override
	public int hashCode() {
		return Objects.hash(payslipId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payslip other = (Payslip) obj;
		return Objects.equals(payslipId, other.payslipId);
	}

/*	@Override
	public String toString() {
		return "Payslip [payslipId=" + payslipId + ", monthOfSalary=" + monthOfSalary + ", totalPaidDays="
				+ totalPaidDays + ", totalSalary=" + totalSalary + ", employee=" + employee + "]";
	}*/

	
	
}
