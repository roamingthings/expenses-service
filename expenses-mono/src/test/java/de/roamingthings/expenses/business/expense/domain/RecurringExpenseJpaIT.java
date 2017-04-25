package de.roamingthings.expenses.business.expense.domain;

import de.roamingthings.SystemPropertyActiveProfileResolver;
import de.roamingthings.junit.category.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
@DataJpaTest
@Category(IntegrationTest.class)
@ActiveProfiles(resolver = SystemPropertyActiveProfileResolver.class)
public class RecurringExpenseJpaIT {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void persistingShouldAddCreationAndModifiedDate() {
        RecurringExpense expense = getRecurringExpenseFixture();

        final RecurringExpense persistedExpense = entityManager.persist(expense);

        assertNotNull(persistedExpense.getId());
        assertNotNull(persistedExpense.getCreatedAt());
        assertNotNull(persistedExpense.getModifiedAt());
        assertThat(persistedExpense.getCreatedAt(), is(persistedExpense.getModifiedAt()));
    }

    @Test
    public void updatingShouldUpdateModifiedDate() {
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