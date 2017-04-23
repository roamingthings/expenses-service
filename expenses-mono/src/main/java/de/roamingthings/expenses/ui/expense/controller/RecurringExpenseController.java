package de.roamingthings.expenses.ui.expense.controller;

import de.roamingthings.expenses.business.expense.domain.RecurringExpense;
import de.roamingthings.expenses.business.expense.service.RecurringExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @RequestMapping(method = RequestMethod.GET)
    public String listRecurringExpenses(Model model) {
        final Iterable<RecurringExpense> recurringExpenseList = recurringExpenseService.findAllRecurringExpenseSummaries();
        model.addAttribute("recurringExpenseList", recurringExpenseList);

        return "/recurring_expenses/list";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateRecurringExpenses(@Valid @ModelAttribute RecurringExpense formExpenseModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/recurring_expenses/edit";
        }

        final RecurringExpense currentRecurringExpense = recurringExpenseService.findRecurringExpense(formExpenseModel.getId());
        currentRecurringExpense.setDescription(formExpenseModel.getDescription());
        currentRecurringExpense.setLabel(formExpenseModel.getLabel());
        currentRecurringExpense.setNextDueDate(formExpenseModel.getNextDueDate());
        currentRecurringExpense.setRecurrencePeriod(formExpenseModel.getRecurrencePeriod());
        currentRecurringExpense.setExpenseType(formExpenseModel.getExpenseType());
        currentRecurringExpense.setAmount(formExpenseModel.getAmount());
        currentRecurringExpense.setCurrency(formExpenseModel.getCurrency());
        currentRecurringExpense.setCreditorName(formExpenseModel.getCreditorName());
        currentRecurringExpense.setNote(formExpenseModel.getNote());

        recurringExpenseService.update(currentRecurringExpense
        );
        return "redirect:/recurring_expenses";
    }

    @GetMapping("/{id}")
    public String editRecurringExpenses(@PathVariable Long id, Model model) {
        final RecurringExpense recurringExpense = recurringExpenseService.findRecurringExpense(id);
        model.addAttribute("recurringExpense", recurringExpense);

        return "/recurring_expenses/edit";
    }
}
