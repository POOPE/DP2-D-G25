package acme.testing.anonymous.duty;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

@Transactional
public class AnonymousDutyShowTest extends AcmeEndeavourTest{
	
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

	@Test
	@Order(20)
	public void negativeTaskShowing() {
		super.clickOnMenu("Duties", "View duties");
		super.clickOnListingRecord(0);

		final String url = this.getCurrentUrl();
		super.signOut();

		super.signIn("administrator", "administrator");
		super.navigate(url, null);
		super.checkPanicExists();
		this.signOut();
	}
}
