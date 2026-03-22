package com.bookvault.event;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

@Component
public class LoanOverdueEventListener {

    private static final Logger log = LoggerFactory.getLogger(LoanOverdueEventListener.class);

    @Async
    @EventListener
    public void handleLoanOverdue(LoanOverdueEvent event) {
        log.info("notification sent for overdue loanId={}, memberId={}, bookId={}",
                event.getLoanId(), event.getMemberId(), event.getBookId());
    }
}