package de.roamingthings.expenses.ui.expense;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/17
 */
@Data
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecurringExpense extends ResourceSupport {
    private Long systemId;

    @NotNull
    @Size(max = 160)
    private String description;

    @Size(max = 160)
    private String label;

    @NotNull
    private Date nextDueDate;

    @NotNull
    private RecurrencePeriod recurrencePeriod;

    @NotNull
    private ExpenseType expenseType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Size(max = 3)
    private String currency;

    @Size(max = 160)
    private String creditorName;

    @Size(max = 2048)
    private String note;


}