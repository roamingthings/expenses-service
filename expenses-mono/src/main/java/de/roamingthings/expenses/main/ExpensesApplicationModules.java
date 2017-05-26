package de.roamingthings.expenses.main;

import de.roamingthings.expenses.health.HealthModule;
import de.roamingthings.expenses.ui.UIModule;
import de.roamingthings.expenses.useraccount.UserAccountModule;
import de.roamingthings.expenses.userprofile.UserProfileModule;
import de.roamingthings.expenses.expense.ExpenseModule;
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
@ComponentScan(basePackageClasses = {ExpensesApplicationModules.class}, excludeFilters = @ComponentScan.Filter(ExpensesApplicationModules.class))
@Configuration

@Import({
        UserAccountModule.class,
        UserProfileModule.class,
        ExpenseModule.class,
        HealthModule.class,
        UIModule.class
})
public @interface ExpensesApplicationModules {
}
