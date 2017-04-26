package de.roamingthings.expenses.ui.expense.controller;

import de.roamingthings.SystemPropertyActiveProfileResolver;
import de.roamingthings.expenses.business.expense.domain.ExpenseType;
import de.roamingthings.expenses.business.expense.domain.RecurrencePeriod;
import de.roamingthings.expenses.business.expense.domain.RecurringExpense;
import de.roamingthings.expenses.business.expense.repository.RecurringExpenseRepository;
import de.roamingthings.junit.category.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Category(IntegrationTest.class)
@ActiveProfiles(resolver = SystemPropertyActiveProfileResolver.class)
public class RecurringExpenseControllerIT {
    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private RecurringExpenseRepository recurringExpenseRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_contain_reference_number() throws Exception {
        RecurringExpense recurringExpense = new RecurringExpense(
                "Description",
                "Label",
                LocalDate.now(),
                RecurrencePeriod.MONTHLY,
                ExpenseType.SUBSCRIPTION,
                BigDecimal.valueOf(1.0),
                "EUR",
                "creditor",
                "abc123",
                "note"
        );
        recurringExpense.setId(1L);

        when(recurringExpenseRepository.findOne(1L)).thenReturn(recurringExpense);

        this.mockMvc.perform(get("/recurring_expenses/details/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id=\"referenceNumber\"")));
    }

    @Test
    public void should_delete_recurring_expense() throws Exception {
        this.mockMvc.perform(get("/recurring_expenses/delete/1"))
                .andDo(print())
                .andExpect(status().isFound());
//                .andExpect(forwardedUrl("redirect:recurring_expenses"));

        verify(recurringExpenseRepository, times(1)).delete(1L);
    }
}