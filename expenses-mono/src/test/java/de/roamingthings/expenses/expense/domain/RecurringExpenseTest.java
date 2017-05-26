package de.roamingthings.expenses.expense.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/27
 */
public class RecurringExpenseTest {
    @Test
    public void updateMutableFieldsFrom() throws Exception {
        final RecurringExpense originalRecurringExpense = new RecurringExpense(
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
        final Date originalCreatedAt = new Date();
        final Date originalModifiedAt = new Date();
        originalRecurringExpense.setId(1L);
        originalRecurringExpense.setCreatedAt(originalCreatedAt);
        originalRecurringExpense.setModifiedAt(originalModifiedAt);
        originalRecurringExpense.setVersion(1);

        final RecurringExpense modifiedRecurringExpense = new RecurringExpense(
                "Modified description",
                "modified label",
                LocalDate.now(),
                RecurrencePeriod.YEARLY,
                ExpenseType.INSURANCE,
                BigDecimal.valueOf(3.45),
                "USD",
                "Modified Creditor",
                "Ref654321",
                "Modified Note");
        modifiedRecurringExpense.setId(1L);
        modifiedRecurringExpense.setCreatedAt(new Date());
        modifiedRecurringExpense.setModifiedAt(new Date());
        modifiedRecurringExpense.setVersion(2);

        originalRecurringExpense.updateMutableFieldsFrom(modifiedRecurringExpense);

        assertThat(originalRecurringExpense.getDescription(), is(modifiedRecurringExpense.getDescription()));
        assertThat(originalRecurringExpense.getLabel(), is(modifiedRecurringExpense.getLabel()));
        assertThat(originalRecurringExpense.getNextDueDate(), is(modifiedRecurringExpense.getNextDueDate()));
        assertThat(originalRecurringExpense.getRecurrencePeriod(), is(modifiedRecurringExpense.getRecurrencePeriod()));
        assertThat(originalRecurringExpense.getExpenseType(), is(modifiedRecurringExpense.getExpenseType()));
        assertThat(originalRecurringExpense.getAmount(), is(modifiedRecurringExpense.getAmount()));
        assertThat(originalRecurringExpense.getCurrency(), is(modifiedRecurringExpense.getCurrency()));
        assertThat(originalRecurringExpense.getCreditorName(), is(modifiedRecurringExpense.getCreditorName()));
        assertThat(originalRecurringExpense.getReferenceNumber(), is(modifiedRecurringExpense.getReferenceNumber()));
        assertThat(originalRecurringExpense.getNote(), is(modifiedRecurringExpense.getNote()));

        assertThat(originalRecurringExpense.getId(), is(1L));
        assertThat(originalRecurringExpense.getCreatedAt(), is(originalCreatedAt));
        assertThat(originalRecurringExpense.getModifiedAt(), is(originalModifiedAt));
        assertThat(originalRecurringExpense.getVersion(), is(1));
    }

}