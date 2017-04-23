package de.roamingthings.expenses;

import de.roamingthings.expenses.business.expense.domain.ExpenseType;
import de.roamingthings.expenses.business.expense.domain.RecurrencePeriod;
import de.roamingthings.expenses.business.expense.domain.RecurringExpense;
import de.roamingthings.expenses.business.expense.repository.RecurringExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/10
 */
@SpringBootApplication
public class ExpensesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpensesServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner initData(RecurringExpenseRepository recurringExpenseRepository) {
        return args -> {
            RecurringExpense expense1 =
                    new RecurringExpense("Recurring Payment 1",
                            "testpayment",
                            LocalDate.now(),
                            RecurrencePeriod.YEARLY,
                            ExpenseType.SUBSCRIPTION,
                            BigDecimal.valueOf(1.23),
                            "EUR",
                            "Creditor 1",
                            "Note 1"
                    );
            recurringExpenseRepository.save(expense1);
            RecurringExpense expense2 =
                    new RecurringExpense("Recurring Payment 2",
                            "testpayment",
                            LocalDate.now(),
                            RecurrencePeriod.MONTHLY,
                            ExpenseType.SUBSCRIPTION,
                            BigDecimal.valueOf(4.56),
                            "EUR",
                            "Creditor 2",
                            "Note 2"
                    );
            recurringExpenseRepository.save(expense2);
        };
/*
                Stream.of("jlong,spring", "dsyder,cloud", "webb,boot", "rwinch,security")
                        .map(tpl -> tpl.split(","))
                        .forEach(tpl -> accountRepository.save(new Account(tpl[0], tpl[1], true)));
*/
    }

}
