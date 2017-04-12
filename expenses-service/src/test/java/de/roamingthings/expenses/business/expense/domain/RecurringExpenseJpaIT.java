package de.roamingthings.expenses.business.expense.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/12
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RecurringExpenseJpaIT {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void persistingShouldAddCreationDate() {
        RecurringExpense expense = new RecurringExpense(
                "Test description",
                "label",
                RecurrencePeriod.MONTHLY,
                ExpenseType.SUBSCRIPTION,
                BigDecimal.valueOf(1.23),
                "EUR",
                "Creditor",
                "Note");

        final RecurringExpense persistedExpense = entityManager.persist(expense);

        assertNotNull(persistedExpense.getId());
        assertNotNull(persistedExpense.getCreatedAt());
    }

}