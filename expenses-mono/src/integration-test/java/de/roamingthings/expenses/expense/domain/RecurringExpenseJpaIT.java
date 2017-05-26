package de.roamingthings.expenses.expense.domain;

import de.roamingthings.SystemPropertyActiveProfileResolver;
import de.roamingthings.expenses.main.ExpensesTrackerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={ExpensesTrackerApplication.class})
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@ActiveProfiles(resolver = SystemPropertyActiveProfileResolver.class)
public class RecurringExpenseJpaIT {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_add_create_and_modified_date_when_persisting() {
        RecurringExpense expense = getRecurringExpenseFixture();

        final RecurringExpense persistedExpense = entityManager.persist(expense);

        assertNotNull(persistedExpense.getId());
        assertNotNull(persistedExpense.getCreatedAt());
        assertNotNull(persistedExpense.getModifiedAt());
        assertThat(persistedExpense.getCreatedAt(), is(persistedExpense.getModifiedAt()));
    }

    @Test
    public void should_update_modified_date_when_updating() {
        RecurringExpense expense = getRecurringExpenseFixture();
        final RecurringExpense persistedExpense = entityManager.persist(expense);
        persistedExpense.setNote("Modified");

        final RecurringExpense updatedExpense = entityManager.merge(persistedExpense);
        entityManager.flush();

        assertThat(updatedExpense.getCreatedAt(), lessThan(updatedExpense.getModifiedAt()));
    }

    private RecurringExpense getRecurringExpenseFixture() {
        return new RecurringExpense(
                    "Test description",
                    "label",
                    LocalDate.now(),
                    RecurrencePeriod.MONTHLY,
                    ExpenseType.SUBSCRIPTION,
                    BigDecimal.valueOf(1.23),
                    "EUR",
                    "Creditor",
                    "Ref12345",
                    "Note");
    }

}