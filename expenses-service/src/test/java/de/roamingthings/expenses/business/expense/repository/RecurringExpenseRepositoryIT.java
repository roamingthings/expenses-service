package de.roamingthings.expenses.business.expense.repository;

import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class RecurringExpenseRepositoryIT {

    @LocalServerPort
    private int port;

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
        final RecurringExpenseRVO recurringExpense = client.retrieve(uri);

        MatcherAssert.assertThat(recurringExpense.getLabel(), is(label));
        MatcherAssert.assertThat(recurringExpense.getExpenseType(), is(expenseType));
        MatcherAssert.assertThat(recurringExpense.getRecurrencePeriod(), is(recurringPeriod));
        MatcherAssert.assertThat(recurringExpense.getAmount(), is(amount));
        MatcherAssert.assertThat(recurringExpense.getCurrency(), is(currency));
        MatcherAssert.assertThat(recurringExpense.getCreditorName(), is(creditorName));
        MatcherAssert.assertThat(recurringExpense.getNote(), is(note));

        return uri;
    }

    private void assertRecurringExpenseExist(final URI... uris) {
        final List<URI> paymentAccounts = client.retrieve();
        MatcherAssert.assertThat(paymentAccounts, hasItems(uris));
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