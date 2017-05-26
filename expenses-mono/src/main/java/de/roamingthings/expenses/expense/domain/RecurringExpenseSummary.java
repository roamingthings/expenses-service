package de.roamingthings.expenses.expense.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/18
 */
@Projection(name = "recurringExpenseSummary", types = {RecurringExpense.class})
public interface RecurringExpenseSummary {

    @JsonProperty("systemId")
    Long getId();

    String getDescription();

    LocalDate getNextDueDate();

    BigDecimal getAmount();

    String getCurrency();

    RecurrencePeriod getRecurrencePeriod();
}
