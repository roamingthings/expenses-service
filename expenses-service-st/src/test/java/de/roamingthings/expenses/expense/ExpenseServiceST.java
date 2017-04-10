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
        final URI createdAccountUri1 = createAndAssertRecurringExpense(
                "Recurring Payment 1",
                "testpayment",
                "MEMBERSHIP",
                "YEARLY",
                BigDecimal.valueOf(1.23),
                "EUR",
                "Creditor 1",
                "Note 1"
        );
        final URI createdAccountUri2 = createAndAssertRecurringExpense(
                "Recurring Payment 2",
                "testpayment",
                "MEMBERSHIP",
                "MONTHLY",
                BigDecimal.valueOf(2.34),
                "USD",
                "Creditor 2",
                "Note 2"
        );
        final URI createdAccountUri3 = createAndAssertRecurringExpense(
                "Recurring Payment 3",
                "testpayment",
                "MEMBERSHIP",
                "WEEKLY",
                BigDecimal.valueOf(5.67),
                "GBP",
                "Creditor 3",
                "Note 3"
        );

        assertRecurringPaymentExist(createdAccountUri1, createdAccountUri2, createdAccountUri3);

        deletePaymentAccount(createdAccountUri1);
        deletePaymentAccount(createdAccountUri2);

        assertNotFound(createdAccountUri1);
        assertNotFound(createdAccountUri2);
        assertFound(createdAccountUri3);
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
        final RecurringExpense recurringExpense = client.retrieve(uri);

        assertThat(recurringExpense.getLabel(), is(label));
        assertThat(recurringExpense.getExpenseType(), is(expenseType));
        assertThat(recurringExpense.getRecurrencePeriod(), is(recurringPeriod));
        assertThat(recurringExpense.getAmount(), is(amount));
        assertThat(recurringExpense.getCurrency(), is(currency));
        assertThat(recurringExpense.getCreditorName(), is(creditorName));
        assertThat(recurringExpense.getNote(), is(note));

        return uri;
    }

    private void assertRecurringPaymentExist(final URI... uris) {
        final List<URI> paymentAccounts = client.retrieve();
        assertThat(paymentAccounts, hasItems(uris));
    }

    private void deletePaymentAccount(URI uri) {
        client.delete(uri);
    }

    private void assertNotFound(URI uri) {
        assertNull(client.retrieve(uri));
    }

    private void assertFound(URI uri) {
        assertNotNull(client.retrieve(uri));
    }
}
