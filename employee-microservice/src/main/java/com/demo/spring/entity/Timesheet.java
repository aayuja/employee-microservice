package com.demo.spring.entity;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;


/*
@Builder/y this 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
*/
@Entity
@Table(name = "TIMESHEET")
public class Timesheet {

	@Id
	@SequenceGenerator(name="timesheet_seq",
	sequenceName="timesheet_sequence", initialValue =150,allocationSize=2)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="timesheet_seq")
	@Column(name="TIMESHEETID",nullable=false)
	private Long timesheetId;
	private int regularHours;
	private int extraHours;
	private String fromDate;
	private String toDate;

	@ManyToOne
	@JoinColumn(name = "EMPID")
	@JsonIgnore
	private Employee employee;

	public Timesheet() {

	}

	public Timesheet(Long timesheetId, int regularHours, int extraHours, String fromDate,
			String toDate) {
		this.timesheetId = timesheetId;
		this.regularHours = regularHours;
		this.extraHours = extraHours;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public Long getTimesheetId() {
		return timesheetId;
	}

	public void setTimesheetId(Long timesheetId) {
		this.timesheetId = timesheetId;
	}

	public int getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(int regularHours) {
		this.regularHours = regularHours;
	}

	public int getExtraHours() {
		return extraHours;
	}

	public void setExtraHours(int extraHours) {
		this.extraHours = extraHours;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
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
		return Objects.hash(timesheetId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Timesheet other = (Timesheet) obj;
		return Objects.equals(timesheetId, other.timesheetId);
	}

	@Override
	public String toString() {
		return "Timesheet [timesheetId=" + timesheetId + ", regularHours=" + regularHours + ", extraHours=" + extraHours
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", employee=" + employee + "]";
	}

	*/

}
