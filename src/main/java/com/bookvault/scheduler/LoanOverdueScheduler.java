package com.bookvault.scheduler;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bookvault.entity.Loan;
import com.bookvault.enums.LoanStatus;
import com.bookvault.event.LoanOverdueEvent;
import com.bookvault.repository.LoanRepository;

@Component
public class LoanOverdueScheduler {

    private final LoanRepository loanRepository;
    private final ApplicationEventPublisher eventPublisher;

    public LoanOverdueScheduler(LoanRepository loanRepository, ApplicationEventPublisher eventPublisher) {
        this.loanRepository = loanRepository;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void markOverdueLoans() {
        List<Loan> overdueLoans = loanRepository.findByStatusAndDueDateBefore(LoanStatus.ACTIVE, LocalDateTime.now());

        for (Loan loan : overdueLoans) {
            loan.setStatus(LoanStatus.OVERDUE);
            loanRepository.save(loan);

            eventPublisher.publishEvent(
                    new LoanOverdueEvent(
                            loan.getId(),
                            loan.getMember().getId(),
                            loan.getBook().getId()
                    )
            );
        }
    }
}