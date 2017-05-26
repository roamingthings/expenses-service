package de.roamingthings.expenses.expense;

import de.roamingthings.expenses.expense.domain.RecurringExpense;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan(basePackageClasses = ExpenseModule.class)
@EntityScan(basePackageClasses = ExpenseModule.class)
@Import(RecurringExpense.class)
public @interface ExpenseModule {
}
