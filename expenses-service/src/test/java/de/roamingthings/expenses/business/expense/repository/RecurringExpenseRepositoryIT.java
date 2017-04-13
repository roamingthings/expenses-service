package de.roamingthings.expenses.business.expense.repository;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
    public void shouldCreateRecurringExpense() throws Exception {
        final URI createdRecurringExpense1 = createAndAssertRecurringExpense(
                "Recurring Payment 1",
                "testpayment",
                new Date(),
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
                new Date(),
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
                new Date(),
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
            final Date nextDueDate,
            final String expenseType,
            final String recurringPeriod,
            final BigDecimal amount,
            final String currency,
            final String creditorName,
            final String note
    ) throws Exception {
        final URI uri = client.create(description,
                label,
                nextDueDate,
                recurringPeriod,
                expenseType,
                amount,
                currency,
                creditorName,
                note);
        final RecurringExpenseRVO recurringExpense = client.retrieve(uri);

        assertThat(recurringExpense.getLabel(), is(label));
        assertThat(recurringExpense.getExpenseType(), is(expenseType));
        assertThat(recurringExpense.getRecurrencePeriod(), is(recurringPeriod));
        assertThat(recurringExpense.getAmount(), is(amount));
        assertThat(recurringExpense.getCurrency(), is(currency));
        assertThat(recurringExpense.getCreditorName(), is(creditorName));
        assertThat(recurringExpense.getNote(), is(note));
        assertEqualDateIgnroingTime(nextDueDate, recurringExpense.getNextDueDate());

        return uri;
    }

    private void assertRecurringExpenseExist(final URI... uris) {
        final List<URI> paymentAccounts = client.retrieve();
        assertThat(paymentAccounts, hasItems(uris));
    }

    private void deleteRecurringExpense(URI uri) {
        client.delete(uri);
    }

    private void assertNotFound(URI uri) throws Exception {
        assertNull(client.retrieve(uri));
    }

    private void assertFound(URI uri) throws Exception {
        assertNotNull(client.retrieve(uri));
    }

    private void assertEqualDateIgnroingTime(Date expectedDate, Date actualDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        assertThat(formatter.format(actualDate), is(formatter.format(expectedDate)));
    }
}