package de.roamingthings.expenses.health.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author alxs
 * @version 2017/04/09
 */
@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping("/status")
    public String status() {
        return "running";
    }
}
