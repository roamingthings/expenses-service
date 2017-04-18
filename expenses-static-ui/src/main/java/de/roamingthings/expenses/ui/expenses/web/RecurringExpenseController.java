package de.roamingthings.expenses.ui.expenses.web;

import de.roamingthings.expenses.ui.expense.RecurringExpense;
import de.roamingthings.expenses.ui.expense.RecurringExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/15
 */
@Controller
@RequestMapping("/recurring_expenses")
public class RecurringExpenseController {

    private RecurringExpenseService recurringExpenseService;

    public RecurringExpenseController(RecurringExpenseService recurringExpenseService) {
        this.recurringExpenseService = recurringExpenseService;
    }

    @RequestMapping
    public String editRecurringExpenses(Model model) {
        final Collection<RecurringExpense> recurringExpenseList = recurringExpenseService.findAllRecurringExpenseSummaries();
        model.addAttribute("recurringExpenseList", recurringExpenseList);

        return "/recurring_expenses/list";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String editRecurringExpenses(@PathVariable Long id, Model model) {
        final RecurringExpense recurringExpense = recurringExpenseService.findRecurringExpense(id);
        model.addAttribute("recurringExpense", recurringExpense);

        return "/recurring_expenses/edit";
    }
}
