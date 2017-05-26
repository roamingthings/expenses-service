package de.roamingthings.expenses.expense.service;

import de.roamingthings.expenses.expense.domain.RecurringExpense;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/23
 */
public interface RecurringExpenseService {
    Iterable<RecurringExpense> findAllRecurringExpenseSummaries();

    RecurringExpense findRecurringExpense(Long id);

    void save(RecurringExpense expense);

    RecurringExpense update(Long id, RecurringExpense expense);

    void delete(Long id);
}
