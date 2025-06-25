package com.LeaveEase.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LeaveEase.Entity.Leave;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

	 List<Leave>findTop5ByOrderByIdDesc();
	  List<Leave>findByStatus(String status);
}
