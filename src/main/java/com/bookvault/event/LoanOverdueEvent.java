package com.bookvault.event;

import java.util.UUID;

public class LoanOverdueEvent {

    private final UUID loanId;
    private final UUID memberId;
    private final UUID bookId;

    public LoanOverdueEvent(UUID loanId, UUID memberId, UUID bookId) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.bookId = bookId;
    }

    public UUID getLoanId() {
        return loanId;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public UUID getBookId() {
        return bookId;
    }
}