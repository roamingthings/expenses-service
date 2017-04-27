package de.roamingthings.expenses.business.expense.service;

import de.roamingthings.expenses.business.expense.domain.RecurringExpense;
import de.roamingthings.expenses.business.expense.repository.RecurringExpenseRepository;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/17
 */
@Service
public class RecurringExpenseServiceImpl implements RecurringExpenseService {
    private RecurringExpenseRepository recurringExpenseRepository;

    public RecurringExpenseServiceImpl(RecurringExpenseRepository recurringExpenseRepository) {
        this.recurringExpenseRepository = recurringExpenseRepository;
    }

    @Override
    public Iterable<RecurringExpense> findAllRecurringExpenseSummaries() {
        final Iterable<RecurringExpense> allRecurringExpenses = recurringExpenseRepository.findAll();

        return allRecurringExpenses;
    }

    @Override
    public RecurringExpense findRecurringExpense(Long id) {
        return recurringExpenseRepository.findOne(id);
    }

    @Override
    public void save(RecurringExpense expense) {
        recurringExpenseRepository.save(expense);
    }

    @Override
    public RecurringExpense update(Long id, RecurringExpense expense) {
        final RecurringExpense currentRecurringExpense = recurringExpenseRepository.findOne(id);

        RecurringExpense updatedRecurringExpense = null;
        if (currentRecurringExpense != null) {
            currentRecurringExpense.updateMutableFieldsFrom(expense);
            updatedRecurringExpense = recurringExpenseRepository.save(currentRecurringExpense);
        }

        return updatedRecurringExpense;
    }

    @Override
    public void delete(Long id) {
        recurringExpenseRepository.delete(id);
    }
}
