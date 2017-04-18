package de.roamingthings.expenses.business.expense.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/18
 */
@Projection(name = "recurringExpenseSummary", types = {RecurringExpense.class})
public interface RecurringExpenseSummary {

    @JsonProperty("systemId")
    Long getId();

    String getDescription();

    Date getNextDueDate();
}
