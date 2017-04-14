package de.roamingthings.expenses.ui.expenses.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/15
 */
@Controller
@RequestMapping("/recurring_expenses")
public class RecurringExpenseController {

    @RequestMapping
    public String editRecurringExpenses() {
        return "/recurring_expenses/list";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String editRecurringExpenses(@PathVariable Long id, Model model) {
        return "/recurring_expenses/edit";
    }
}
