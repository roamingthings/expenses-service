package de.roamingthings.expenses.business.expense.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/10
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "recurring_expense")
public class RecurringExpense {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @NotNull
    private String description;

    private String label;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence_period")
    private RecurrencePeriod recurrencePeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_type")
    private ExpenseType expenseType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(length = 3)
    private String currency;

    @Column(name = "creditor_name")
    private String creditorName;

    @Column
    private String note;

    public RecurringExpense(String description, String label, RecurrencePeriod recurrencePeriod, ExpenseType expenseType, BigDecimal amount, String currency, String creditorName, String note) {
        this.description = description;
        this.label = label;
        this.recurrencePeriod = recurrencePeriod;
        this.expenseType = expenseType;
        this.amount = amount;
        this.currency = currency;
        this.creditorName = creditorName;
        this.note = note;
    }
}