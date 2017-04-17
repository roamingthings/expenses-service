package de.roamingthings.expenses.ui;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/17
 */
@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type= {EnableHypermediaSupport.HypermediaType.HAL})
public class Config {
}