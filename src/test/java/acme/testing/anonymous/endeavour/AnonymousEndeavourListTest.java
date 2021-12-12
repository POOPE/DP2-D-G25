package acme.testing.anonymous.endeavour;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

@Transactional
public class AnonymousEndeavourListTest extends AcmeEndeavourTest{

	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/duty/show-duties.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTaskListing(final int recordIndex, final String title, final String description, final String executionStart, final String executionEnd, final String link, final String workload, final String isPublic) {
		super.navigateHome();
		super.clickOnMenu("Duties", "View duties");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, description);
		super.checkColumnHasValue(recordIndex, 2, executionStart);
		super.checkColumnHasValue(recordIndex, 3, executionEnd);
		super.checkColumnHasValue(recordIndex, 4, workload);
		super.checkColumnHasValue(recordIndex, 5, isPublic);

	}

	@Test
	@Order(20)
	public void negativeTaskListing() {
		super.signIn("administrator", "administrator");
		super.navigate("/anonymous/duty/list", "");
		this.checkPanicExists();
		this.signOut();
	}
}
