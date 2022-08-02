package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.spring.entity.Timesheet;

@Repository
public interface TimesheetRepo extends JpaRepository<Timesheet,Long> {

}
