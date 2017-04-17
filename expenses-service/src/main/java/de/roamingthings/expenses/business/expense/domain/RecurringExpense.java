package de.roamingthings.expenses.business.expense.domain;

import de.roamingthings.expenses.metadata.Creatable;
import de.roamingthings.expenses.metadata.Modifiable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

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
public class RecurringExpense implements Creatable, Modifiable {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at")
    private Date modifiedAt;

    @NotNull
    @Size(max = 160)
    @Column(length = 160)
    private String description;

    @Column(length = 160)
    private String label;

    @NotNull
    @Column(name = "next_due_date")
    @Temporal(TemporalType.DATE)
    private Date nextDueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence_period", length = 80)
    private RecurrencePeriod recurrencePeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_type", length = 80)
    private ExpenseType expenseType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Size(max = 3)
    @Column(length = 3)
    private String currency;

    @Size(max = 160)
    @Column(name = "creditor_name", length = 160)
    private String creditorName;

    @Size(max = 2048)
    @Column(length = 2048)
    private String note;

    public RecurringExpense(
            String description,
            String label,
            Date nextDueDate,
            RecurrencePeriod recurrencePeriod,
            ExpenseType expenseType,
            BigDecimal amount,
            String currency,
            String creditorName,
            String note) {
        this.description = description;
        this.label = label;
        this.nextDueDate = nextDueDate;
        this.recurrencePeriod = recurrencePeriod;
        this.expenseType = expenseType;
        this.amount = amount;
        this.currency = currency;
        this.creditorName = creditorName;
        this.note = note;
    }
}