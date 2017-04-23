package de.roamingthings.expenses.ui.expense.controller;

import com.sun.tools.javac.util.List;
import de.roamingthings.expenses.ui.expense.ExpenseType;
import de.roamingthings.expenses.ui.expense.RecurrencePeriod;
import de.roamingthings.expenses.ui.expense.RecurringExpense;
import de.roamingthings.expenses.ui.expense.RecurringExpenseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RecurringExpenseControllerIT {

    @MockBean
    private RecurringExpenseService recurringExpenseService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_list_recurring_expenses() throws Exception {
        final List<RecurringExpense> recurringExpenseList = List.of(new RecurringExpense(
                1L,
                "re description",
                "re label",
                LocalDate.now(),
                RecurrencePeriod.MONTHLY,
                ExpenseType.SUBSCRIPTION,
                BigDecimal.valueOf(1.23),
                "EUR",
                "re creditor name",
                "re note"));
        given(this.recurringExpenseService.findAllRecurringExpenseSummaries()).willReturn(recurringExpenseList);

        this.mockMvc.perform(get("/recurring_expenses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/recurring_expenses/list"));

        verify(recurringExpenseService, times(1)).findAllRecurringExpenseSummaries();
    }
}