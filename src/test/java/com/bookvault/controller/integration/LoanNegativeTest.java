package com.bookvault.controller.integration;


import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.bookvault.entity.Book;
import com.bookvault.entity.Member;
import com.bookvault.enums.MembershipStatus;
import com.bookvault.repository.BookRepository;
import com.bookvault.repository.MemberRepository;
import com.bookvault.service.LoanService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoanNegativeTest {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void borrowShouldFailWhenAvailableCopiesIsZero() {
        Book book = new Book();
        book.setIsbn("978-1234567800");
        book.setTitle("Unavailable Book");
        book.setAuthor("Nobody");
        book.setGenre("Test");
        book.setTotalCopies(1);
        book.setAvailableCopies(0);
        book = bookRepository.save(book);

        Member member = new Member();
        member.setEmail("negative@test.com");
        member.setName("Negative User");
        member.setMembershipStatus(MembershipStatus.ACTIVE);
        member = memberRepository.save(member);

        Book finalBook = book;
        Member finalMember = member;

        assertThatThrownBy(() -> loanService.borrow(finalBook.getId(), finalMember.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("No copies available");
    }
}
