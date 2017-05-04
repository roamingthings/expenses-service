package de.roamingthings.expenses.ui.expense.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/04
 */
@Controller
public class UserProfileController {

    @RequestMapping("/userprofile")
    public String editUserProfile(Principal principal) {
        return "/";
    }
}
