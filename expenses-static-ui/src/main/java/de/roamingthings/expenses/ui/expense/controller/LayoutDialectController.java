package de.roamingthings.expenses.ui.expense.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LayoutDialectController {

    @RequestMapping(value = "/layout", method = RequestMethod.GET)
    public String getNewPage(Model model) {
        return "templatetest";
    }

}