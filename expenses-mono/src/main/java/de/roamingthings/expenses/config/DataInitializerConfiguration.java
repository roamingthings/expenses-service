package de.roamingthings.expenses.config;

import de.roamingthings.expenses.business.expense.repository.RecurringExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/07
 */
@Configuration
public class DataInitializerConfiguration {
    private final List<DataInitializer> dataInitializers;

    public DataInitializerConfiguration(List<DataInitializer> dataInitializers) {
        this.dataInitializers = dataInitializers;
    }

    @Bean
    @Transactional
    CommandLineRunner initData(RecurringExpenseRepository recurringExpenseRepository) {
        return args -> {
            dataInitializers.forEach(DataInitializer::initializeData);
        };
    }
}
