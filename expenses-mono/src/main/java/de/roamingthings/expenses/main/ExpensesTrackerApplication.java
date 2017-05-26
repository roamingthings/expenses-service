package de.roamingthings.expenses.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/10
 */
@ExpensesApplicationModules
@SpringBootApplication
//@EnableAutoConfiguration
public class ExpensesTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpensesTrackerApplication.class, args);
    }

}
