package de.roamingthings.expenses.health;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/09
 */
public class SmokeST {

    private WebTarget baseTarget;

    @Before
    public void setUp() {
        baseTarget = ClientBuilder.newClient().target("http://localhost:9191/health");
    }

    @Test
    public void shouldRetrieveRunningFromHealthResource() {
        final Response response = baseTarget.path("status").request(MediaType.APPLICATION_JSON_TYPE).get();
        assertStatus(Response.Status.OK, response);
        assertThat(response.readEntity(String.class), is("running"));
    }

    private void assertStatus(Response.Status expectedStatus, Response response) {
        assertThat(response.getStatus(), is(expectedStatus.getStatusCode()));
    }
}
