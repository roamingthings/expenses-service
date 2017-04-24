package de.roamingthings.expenses.ui.expense.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/13
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}