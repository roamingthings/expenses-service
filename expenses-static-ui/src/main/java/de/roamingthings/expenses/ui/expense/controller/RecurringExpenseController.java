package de.roamingthings.expenses.ui.expense.controller;

import de.roamingthings.expenses.ui.expense.RecurringExpense;
import de.roamingthings.expenses.ui.expense.RecurringExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping
    public String listRecurringExpenses(Model model) {
        final Collection<RecurringExpense> recurringExpenseList = recurringExpenseService.findAllRecurringExpenseSummaries();
        model.addAttribute("recurringExpenseList", recurringExpenseList);

        return "/recurring_expenses/list";
    }

    @PostMapping
    public String postRecurringExpenses(@Valid @ModelAttribute RecurringExpense expense, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/recurring_expenses/edit";
        }

        recurringExpenseService.update(expense, expense.getSystemId());
        return "redirect:/recurring_expenses";
    }

    @GetMapping("/{id}")
    public String editRecurringExpenses(@PathVariable Long id, Model model) {
        final RecurringExpense recurringExpense = recurringExpenseService.findRecurringExpense(id);
        recurringExpense.setSystemId(id);
        model.addAttribute("recurringExpense", recurringExpense);

        return "/recurring_expenses/edit";
    }
}
