package de.roamingthings.expenses.business.expense.repository;

import de.roamingthings.expenses.business.expense.domain.RecurringExpense;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/10
 */
@RepositoryRestResource(path = "recurring_expenses")
public interface RecurringExpenseRepository extends PagingAndSortingRepository<RecurringExpense, Long> {
    RecurringExpense findByDescription(String description);
}
