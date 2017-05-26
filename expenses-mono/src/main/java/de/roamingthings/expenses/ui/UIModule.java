package de.roamingthings.expenses.ui;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan(basePackageClasses = UIModule.class)
public @interface UIModule {
}
