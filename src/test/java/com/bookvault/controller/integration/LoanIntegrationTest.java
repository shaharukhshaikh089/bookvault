package com.bookvault.controller.integration;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import com.bookvault.entity.Book;
import com.bookvault.entity.Loan;
import com.bookvault.entity.Member;
import com.bookvault.enums.MembershipStatus;
import com.bookvault.repository.BookRepository;
import com.bookvault.repository.LoanRepository;
import com.bookvault.repository.MemberRepository;
import com.bookvault.service.LoanService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoanIntegrationTest {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Test
    void borrowAndReturnFlow_shouldWork() {
        Book book = new Book();
        book.setIsbn("978-1234567899");
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setGenre("Test");
        book.setTotalCopies(2);
        book.setAvailableCopies(2);
        book = bookRepository.save(book);

        Member member = new Member();
        member.setEmail("flow@test.com");
        member.setName("Flow User");
        member.setMembershipStatus(MembershipStatus.ACTIVE);
        member = memberRepository.save(member);

        Loan loan = loanService.borrow(book.getId(), member.getId());
        Book borrowedBook = bookRepository.findById(book.getId()).orElseThrow();

        assertThat(loan.getId()).isNotNull();
        assertThat(borrowedBook.getAvailableCopies()).isEqualTo(1);

        loanService.returnLoan(loan.getId());
        Book returnedBook = bookRepository.findById(book.getId()).orElseThrow();

        assertThat(returnedBook.getAvailableCopies()).isEqualTo(2);
        assertThat(loanRepository.findById(loan.getId()).orElseThrow().getReturnedAt()).isNotNull();
    }
}