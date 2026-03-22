package com.bookvault.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookvault.entity.Loan;
import com.bookvault.entity.Member;
import com.bookvault.response.ApiResponse;
import com.bookvault.service.LoanService;
import com.bookvault.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final LoanService loanService;

    public MemberController(MemberService memberService, LoanService loanService) {
        this.memberService = memberService;
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Member>> create(@Valid @RequestBody Member member) {
        return ResponseEntity.ok(new ApiResponse<>(memberService.create(member), null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Member>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(memberService.getAll(), null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Member>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponse<>(memberService.getById(id), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Member>> update(@PathVariable UUID id, @Valid @RequestBody Member member) {
        return ResponseEntity.ok(new ApiResponse<>(memberService.update(id, member), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        memberService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Deleted", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Member>>> search(@RequestParam String q) {
        return ResponseEntity.ok(new ApiResponse<>(memberService.search(q), null));
    }

    @GetMapping("/{id}/loans")
    public ResponseEntity<ApiResponse<List<Loan>>> getMemberLoans(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponse<>(loanService.getLoansByMember(id), null));
    }
}