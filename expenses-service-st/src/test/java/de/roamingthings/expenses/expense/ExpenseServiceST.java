package de.roamingthings.expenses.expense;

import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/10
 */
public class ExpenseServiceST {
    @Rule
    public RecurringExpenseClient client = new RecurringExpenseClient("http://localhost:8080/recurring_expenses");

    @Test
    public void shouldCreateRecurringExpense() {
        final URI createdRecurringExpense1 = createAndAssertRecurringExpense(
                "Recurring Payment 1",
                "testpayment",
                "MEMBERSHIP",
                "YEARLY",
                BigDecimal.valueOf(1.23),
                "EUR",
                "Creditor 1",
                "Note 1"
        );
        final URI createdRecurringExpense2 = createAndAssertRecurringExpense(
                "Recurring Payment 2",
                "testpayment",
                "MEMBERSHIP",
                "MONTHLY",
                BigDecimal.valueOf(2.34),
                "USD",
                "Creditor 2",
                "Note 2"
        );
        final URI createdRecurringExpense3 = createAndAssertRecurringExpense(
                "Recurring Payment 3",
                "testpayment",
                "MEMBERSHIP",
                "WEEKLY",
                BigDecimal.valueOf(5.67),
                "GBP",
                "Creditor 3",
                "Note 3"
        );

        assertRecurringExpenseExist(createdRecurringExpense1, createdRecurringExpense2, createdRecurringExpense3);

        deleteRecurringExpense(createdRecurringExpense1);
        deleteRecurringExpense(createdRecurringExpense2);

        assertNotFound(createdRecurringExpense1);
        assertNotFound(createdRecurringExpense2);
        assertFound(createdRecurringExpense3);
    }

    private URI createAndAssertRecurringExpense(
            final String description,
            final String label,
            final String expenseType,
            final String recurringPeriod,
            final BigDecimal amount,
            final String currency,
            final String creditorName,
            final String note
    ) {
        final URI uri = client.create(description,
                label,
                recurringPeriod,
                expenseType,
                amount,
                currency,
                creditorName,
                note);
        final RecurringExpenseRVO recurringExpenseRVO = client.retrieve(uri);

        assertThat(recurringExpenseRVO.getLabel(), is(label));
        assertThat(recurringExpenseRVO.getExpenseType(), is(expenseType));
        assertThat(recurringExpenseRVO.getRecurrencePeriod(), is(recurringPeriod));
        assertThat(recurringExpenseRVO.getAmount(), is(amount));
        assertThat(recurringExpenseRVO.getCurrency(), is(currency));
        assertThat(recurringExpenseRVO.getCreditorName(), is(creditorName));
        assertThat(recurringExpenseRVO.getNote(), is(note));

        return uri;
    }

    private void assertRecurringExpenseExist(final URI... uris) {
        final List<URI> paymentAccounts = client.retrieve();
        assertThat(paymentAccounts, hasItems(uris));
    }

    private void deleteRecurringExpense(URI uri) {
        client.delete(uri);
    }

    private void assertNotFound(URI uri) {
        assertNull(client.retrieve(uri));
    }

    private void assertFound(URI uri) {
        assertNotNull(client.retrieve(uri));
    }
}
