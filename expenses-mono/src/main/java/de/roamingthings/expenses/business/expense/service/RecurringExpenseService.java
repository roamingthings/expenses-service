package de.roamingthings.expenses.business.expense.service;

import de.roamingthings.expenses.business.expense.domain.RecurringExpense;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/23
 */
public interface RecurringExpenseService {
    Iterable<RecurringExpense> findAllRecurringExpenseSummaries();

    RecurringExpense findRecurringExpense(Long id);

    void update(RecurringExpense expense);
}