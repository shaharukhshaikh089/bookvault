package com.bookvault.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bookvault.entity.Book;
import com.bookvault.entity.Loan;
import com.bookvault.entity.Member;
import com.bookvault.enums.LoanStatus;
import com.bookvault.enums.MembershipStatus;
import com.bookvault.repository.BookRepository;
import com.bookvault.repository.LoanRepository;
import com.bookvault.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public Loan borrow(UUID bookId, UUID memberId) {
        if (bookId == null || memberId == null) {
            throw new RuntimeException("Invalid request");
        }

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        if (member.getMembershipStatus() == MembershipStatus.SUSPENDED) {
            throw new RuntimeException("Member is suspended");
        }

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available");
        }

        long activeLoans = loanRepository.countByMemberAndStatus(member, LoanStatus.ACTIVE);
        if (activeLoans >= 3) {
            throw new RuntimeException("Max 3 active loans allowed");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setBorrowedAt(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusDays(14));
        loan.setStatus(LoanStatus.ACTIVE);

        return loanRepository.save(loan);
    }

    public Loan returnLoan(UUID loanId) {
        if (loanId == null) {
            throw new RuntimeException("Loan ID cannot be null");
        }

        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() != LoanStatus.ACTIVE && loan.getStatus() != LoanStatus.OVERDUE) {
            throw new RuntimeException("Loan already closed");
        }

        loan.setReturnedAt(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    public Page<Loan> getOverdueLoans(Pageable pageable) {
        Page<Loan> page = loanRepository.findByStatusAndDueDateBefore(LoanStatus.ACTIVE, LocalDateTime.now(), pageable);
        page.forEach(loan -> loan.setStatus(LoanStatus.OVERDUE));
        return loanRepository.saveAll(page.getContent()).isEmpty() ? page : loanRepository.findByStatusAndDueDateBefore(LoanStatus.OVERDUE, LocalDateTime.now().plusYears(100), pageable);
    }

    public List<Loan> getLoansByMember(UUID memberId) {
        if (memberId == null) {
            throw new RuntimeException("MemberId cannot be null");
        }
        memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        return loanRepository.findByMember_Id(memberId);
    }
}