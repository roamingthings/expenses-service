package de.roamingthings.expenses;

import de.roamingthings.SystemPropertyActiveProfileResolver;
import de.roamingthings.expenses.main.ExpensesTrackerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpensesTrackerApplication.class)
@ActiveProfiles(resolver = SystemPropertyActiveProfileResolver.class)
public class ExpensesTrackerApplicationIT {

	@Test
	public void should_load_application_context() {
	}

}
