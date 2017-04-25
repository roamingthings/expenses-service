package de.roamingthings.expenses;

import de.roamingthings.expenses.business.expense.domain.ExpenseType;
import de.roamingthings.expenses.business.expense.domain.RecurrencePeriod;
import de.roamingthings.expenses.business.expense.domain.RecurringExpense;
import de.roamingthings.expenses.business.expense.repository.RecurringExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/25
 */
@Configuration
@Profile("dev")
public class DevelopProfileDataInitializer {
    @Bean
    CommandLineRunner initData(RecurringExpenseRepository recurringExpenseRepository) {
        return args -> {
            if (recurringExpenseRepository.findByDescription("Recurring Payment 1") == null) {

                RecurringExpense expense1 =
                        new RecurringExpense("Recurring Payment 1",
                                "testpayment",
                                LocalDate.now(),
                                RecurrencePeriod.YEARLY,
                                ExpenseType.SUBSCRIPTION,
                                BigDecimal.valueOf(1.23),
                                "EUR",
                                "Creditor 1",
                                "Ref123",
                                "Note 1"
                        );
                recurringExpenseRepository.save(expense1);
            }

            if (recurringExpenseRepository.findByDescription("Recurring Payment 2") == null) {
                RecurringExpense expense2 =
                        new RecurringExpense("Recurring Payment 2",
                                "testpayment",
                                LocalDate.now(),
                                RecurrencePeriod.MONTHLY,
                                ExpenseType.SUBSCRIPTION,
                                BigDecimal.valueOf(4.56),
                                "EUR",
                                "Creditor 2",
                                "Ref345",
                                "Note 2"
                        );
                recurringExpenseRepository.save(expense2);
            }
        };
    }
}
