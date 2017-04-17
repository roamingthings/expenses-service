package de.roamingthings.expenses.ui.expense;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/17
 */
@Service
public class RecurringExpenseService {

    private static final String URI_RECURRING_EXPENSES_RESOURCE = "http://localhost:{port}/recurring_expenses";
    private static final int PORT = 9191;


    private RestTemplate restTemplate;

    public RecurringExpenseService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Collection<RecurringExpense> getRecurringExpenseList() {
        ResponseEntity<Resources<RecurringExpense>> responseEntity =
                restTemplate.exchange(
                        URI_RECURRING_EXPENSES_RESOURCE,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Resources<RecurringExpense>>() {
                        }, PORT
                );

        Collection<RecurringExpense> content = null;
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            content = responseEntity.getBody().getContent();
        }

        return content;
    }
}
