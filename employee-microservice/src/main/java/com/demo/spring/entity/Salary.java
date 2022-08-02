package com.demo.spring.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "SALARY")
public class Salary {
	
	@Id
	@SequenceGenerator(name="salary_seq",
	sequenceName="salary_sequence", initialValue =200,allocationSize=2)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="salary_seq")
	@Column(name="SALARYID",nullable=false)
	private Long salaryId;
	private int basic;
	private int hra;
	private int totalSalary;
	
	public Salary() {
		
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="EMPID")
	@JsonIgnore
	private Employee employee;


	public Salary(Long salaryId, int basic, int hra, int totalSalary) {
		this.salaryId = salaryId;
		this.basic = basic;
		this.hra = hra;
		this.totalSalary = totalSalary;
	}


	public Long getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(Long salaryId) {
		this.salaryId = salaryId;
	}

	public int getBasic() {
		return basic;
	}

	public void setBasic(int basic) {
		this.basic = basic;
	}

	public int getHra() {
		return hra;
	}

	public void setHra(int hra) {
		this.hra = hra;
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

/*
	@Override
	public int hashCode() {
		return Objects.hash(salaryId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Salary other = (Salary) obj;
		return Objects.equals(salaryId, other.salaryId);
	}


	@Override
	public String toString() {
		return "Salary [salaryId=" + salaryId + ", basic=" + basic + ", hra=" + hra + ", totalSalary=" + totalSalary
				+ "]";
	}
	*/
	
}
