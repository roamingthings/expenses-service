package de.roamingthings.expenses.expense;

import org.junit.rules.ExternalResource;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/10
 */
public class RecurringExpenseClient extends ExternalResource {

    private final String baseURI;
    private Client client;
    private WebTarget baseTarget;

    public RecurringExpenseClient(String baseURI) {
        this.baseURI = baseURI;
    }


    @Override
    protected void before() throws Throwable {
        client = ClientBuilder.newClient();

        baseTarget = client.target(baseURI);
    }

    public URI create(
            final String description,
            final String label,
            final String recurrencePeriod,
            final String expenseType,
            final BigDecimal amount,
            final String currency,
            final String creditorName,
            final String note
    ) {
        final JsonObject expenseObject = Json.createObjectBuilder()
                .add("description", description)
                .add("label", label)
                .add("recurrencePeriod", recurrencePeriod)
                .add("expenseType", expenseType)
                .add("amount", amount)
                .add("currency", currency)
                .add("creditorName", creditorName)
                .add("note", note)
                .build();

        final Response response = baseTarget
                .request()
                .post(Entity.json(expenseObject));

        assertStatus(Response.Status.CREATED, response);

        final URI uri = response.getLocation();
        assertNotNull(uri);

        return uri;
    }

    public List<URI> retrieve() {
        final Response response = baseTarget.request(MediaType.APPLICATION_JSON_TYPE).get();
        assertStatus(Response.Status.OK, response);

        final JsonArray jsonArray = response.readEntity(JsonObject.class).getJsonObject("_embedded").getJsonArray("recurringExpenses");
        assertThat(jsonArray, notNullValue());
        return jsonArray.getValuesAs(JsonObject.class).stream()
                .map(o -> o.getJsonObject("_links").getJsonObject("self").getString("href"))
                .map(URI::create)
                .collect(toList());
    }

    public RecurringExpenseRVO retrieve(URI uri) {
        final Response response = client.target(uri).request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }
        assertStatus(Response.Status.OK, response);

        final JsonObject object = response.readEntity(JsonObject.class);

        return new RecurringExpenseRVO(
                object.getString("description"),
                object.getString("label"),
                object.getString("recurrencePeriod"),
                object.getString("expenseType"),
                object.getJsonNumber("amount").bigDecimalValue(),
                object.getString("currency"),
                object.getString("creditorName"),
                object.getString("note"));
    }


    public void delete(URI uri) {
        final Response response = client.target(uri).request().delete();
        assertStatus(Response.Status.NO_CONTENT, response);
    }

    private void assertStatus(Response.Status expectedStatus, Response response) {
        assertThat(response.getStatus(), is(expectedStatus.getStatusCode()));
    }
}
