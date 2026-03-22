package com.bookvault.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookvault.entity.Loan;
import com.bookvault.response.ApiResponse;
import com.bookvault.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Loan>> borrow(@RequestParam UUID bookId, @RequestParam UUID memberId) {
        return ResponseEntity.ok(new ApiResponse<>(loanService.borrow(bookId, memberId), null));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<ApiResponse<Loan>> returnBook(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponse<>(loanService.returnLoan(id), null));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<ApiResponse<List<Loan>>> getLoansByMember(@PathVariable UUID memberId) {
        return ResponseEntity.ok(new ApiResponse<>(loanService.getLoansByMember(memberId), null));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<Page<Loan>>> getOverdueLoans(
            @PageableDefault(size = 10, sort = "dueDate") Pageable pageable) {
        return ResponseEntity.ok(new ApiResponse<>(loanService.getOverdueLoans(pageable), null));
    }
}