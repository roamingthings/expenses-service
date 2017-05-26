package de.roamingthings.expenses.ui.expense.controller;

import de.roamingthings.expenses.expense.domain.RecurringExpense;
import de.roamingthings.expenses.expense.service.RecurringExpenseService;
import lombok.extern.java.Log;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/15
 */
@Log
@Controller
@RequestMapping("/recurring_expenses")
public class RecurringExpenseController {

    private RecurringExpenseService recurringExpenseService;

    public RecurringExpenseController(RecurringExpenseService recurringExpenseService) {
        this.recurringExpenseService = recurringExpenseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String listRecurringExpenses(Model model) {
        final Iterable<RecurringExpense> recurringExpenseList = recurringExpenseService.findAllRecurringExpenseSummaries();
        model.addAttribute("recurringExpenseList", recurringExpenseList);

        return "recurring_expenses/list";
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String updateRecurringExpenses(@Valid @ModelAttribute RecurringExpense formExpenseModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "recurring_expenses/form";
        }

        if (formExpenseModel.getId() != null) {
            recurringExpenseService.update(formExpenseModel.getId(), formExpenseModel);
        } else {
            recurringExpenseService.save(formExpenseModel);
        }

        return "redirect:/recurring_expenses";
    }

    @RequestMapping(path = "/details/{id}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String editRecurringExpenses(@PathVariable Long id, Model model) {
        final RecurringExpense recurringExpense = recurringExpenseService.findRecurringExpense(id);
        model.addAttribute("recurringExpense", recurringExpense);

        return "recurring_expenses/form";
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public String deleteRecurringExpenseAjax(@PathVariable Long id, Model model, HttpServletRequest request) {
        recurringExpenseService.delete(id);

        if ("DELETE".equals(request.getMethod())) {
            final Iterable<RecurringExpense> recurringExpenseList = recurringExpenseService.findAllRecurringExpenseSummaries();
            model.addAttribute("recurringExpenseList", recurringExpenseList);

            return "recurring_expenses/summary_table";
        }

        return "redirect:/recurring_expenses";
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String newRecurringExpenses(Model model) {
        final RecurringExpense recurringExpense = new RecurringExpense();
        recurringExpense.setNextDueDate(LocalDate.now());
        model.addAttribute("recurringExpense", recurringExpense);

        return "recurring_expenses/form";
    }
}
