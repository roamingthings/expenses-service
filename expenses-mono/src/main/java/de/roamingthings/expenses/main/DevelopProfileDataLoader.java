package de.roamingthings.expenses.main;

import de.roamingthings.expenses.expense.domain.ExpenseType;
import de.roamingthings.expenses.expense.domain.RecurrencePeriod;
import de.roamingthings.expenses.expense.domain.RecurringExpense;
import de.roamingthings.expenses.expense.repository.RecurringExpenseRepository;
import de.roamingthings.expenses.main.config.DataInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/25
 */
@Configuration
@Profile({"dev", "test", "it"})
public class DevelopProfileDataLoader implements DataInitializer {
    private final RecurringExpenseRepository recurringExpenseRepository;

    public DevelopProfileDataLoader(RecurringExpenseRepository recurringExpenseRepository) {
        this.recurringExpenseRepository = recurringExpenseRepository;
    }

    @Override
    public void initializeData() {
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
    }
}
