package de.roamingthings.expenses.ui.expense.controller;

import de.roamingthings.expenses.expense.domain.RecurringExpense;
import de.roamingthings.expenses.expense.service.RecurringExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/13
 */
@Controller
public class HomeController {

    @Inject
    private RecurringExpenseService recurringExpenseService;

    @RequestMapping({"/", "/home"})
    public String index(Model model) {
        final Iterable<RecurringExpense> recurringExpenseList = recurringExpenseService.findAllRecurringExpenseSummaries();
        model.addAttribute("recurringExpenseList", recurringExpenseList);

        return "index";
    }
}