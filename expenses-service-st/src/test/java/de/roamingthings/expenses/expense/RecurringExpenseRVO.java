package de.roamingthings.expenses.expense;

import java.math.BigDecimal;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/10
 */
public class RecurringExpenseRVO {
    private final String description;

    private final String label;

    private final String recurrencePeriod;

    private final String expenseType;

    private final BigDecimal amount;

    private final String currency;

    private final String creditorName;

    private final String note;

    public RecurringExpenseRVO(String description, String label, String recurrencePeriod, String expenseType, BigDecimal amount, String currency, String creditorName, String note) {
        this.description = description;
        this.label = label;
        this.recurrencePeriod = recurrencePeriod;
        this.expenseType = expenseType;
        this.amount = amount;
        this.currency = currency;
        this.creditorName = creditorName;
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public String getLabel() {
        return label;
    }

    public String getRecurrencePeriod() {
        return recurrencePeriod;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public String getNote() {
        return note;
    }
}
