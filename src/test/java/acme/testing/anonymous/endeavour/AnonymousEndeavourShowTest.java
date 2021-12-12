package acme.testing.anonymous.endeavour;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

@Transactional
public class AnonymousEndeavourShowTest extends AcmeEndeavourTest{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/duty/show-duties.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void anonymousTaskShow(final int recordIndex, final String title, final String description, final String executionStart, final String executionEnd, final String link, final String workload, final String isPublic) {
		super.navigateHome();
		super.clickOnMenu("Duties", "View duties");

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("executionStart", executionStart);
		super.checkInputBoxHasValue("executionEnd", executionEnd);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("isPublic", isPublic);

	}

	/**
	 * 
	 * We check that we can't show tasks logged as an administrator.
	 * Once logged in as administrator, the first thing to do is to check that the anonymous section does not exist. Then we try to access by url
	 * to the list of tasks, and check that the result is an error page, as it is not authorised.
	 * 
	 */

	@Test
	@Order(20)
	public void negativeTaskShowing() {
		super.signIn("manager1", "manager1");
		super.clickOnMenu("Manager", "List my tasks");
		super.clickOnListingRecord(0);

		final String url = this.getCurrentUrl();
		super.signOut();

		super.signIn("administrator", "administrator");
		super.navigate(url, "");
		super.checkPanicExists();
		this.signOut();
	}
}
