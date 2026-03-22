package com.bookvault.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookvault.entity.Loan;
import com.bookvault.entity.Member;
import com.bookvault.enums.LoanStatus;


public interface LoanRepository extends JpaRepository<Loan, UUID> {

	 long countByMemberAndStatus(Member member, LoanStatus status);
	    List<Loan> findByStatusAndDueDateBefore(LoanStatus status, LocalDateTime dueDate);
	    Page<Loan> findByStatusAndDueDateBefore(LoanStatus status, LocalDateTime dueDate, Pageable pageable);
	    List<Loan> findByMember_Id(UUID memberId);}
