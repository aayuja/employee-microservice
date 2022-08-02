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
@Table(name = "LEAVES")
public class Leave {

	@Id
	@SequenceGenerator(name = "leave_seq", sequenceName = "leave_sequence", 
	initialValue = 50, allocationSize = 2)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leave_seq")
	@Column(name = "LEAVEID", nullable = false)
	private Long leaveId;
	private String fromdateOfLeave;
	private String todateOfLeave;
	private String reasonOfLeave;
	private String status;

	public Leave() {

	}

	@ManyToOne
	@JoinColumn(name = "EMPID")
	@JsonIgnore
	private Employee employee;

	
	
	public Leave(Long leaveId, String fromdateOfLeave, String todateOfLeave, String reasonOfLeave,String status) {
		this.leaveId = leaveId;
		this.fromdateOfLeave = fromdateOfLeave;
		this.todateOfLeave = todateOfLeave;
		this.reasonOfLeave = reasonOfLeave;
		this.status = status;
	}

	public Long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}

	public String getFromdateOfLeave() {
		return fromdateOfLeave;
	}

	public void setFromdateOfLeave(String fromdateOfLeave) {
		this.fromdateOfLeave = fromdateOfLeave;
	}

	public String getTodateOfLeave() {
		return todateOfLeave;
	}

	public void setTodateOfLeave(String todateOfLeave) {
		this.todateOfLeave = todateOfLeave;
	}

	public String getReasonOfLeave() {
		return reasonOfLeave;
	}

	public void setReasonOfLeave(String reasonOfLeave) {
		this.reasonOfLeave = reasonOfLeave;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(leaveId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Leave other = (Leave) obj;
		return Objects.equals(leaveId, other.leaveId);
	}

	/*@Override
	public String toString() {
		return "Leave [leaveId=" + leaveId + ", fromdateOfLeave=" + fromdateOfLeave + ", todateOfLeave=" + todateOfLeave
				+ ", reasonOfLeave=" + reasonOfLeave + ", employee=" + employee + "]";
	}
*/
	
}
