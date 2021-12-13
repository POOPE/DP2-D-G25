package acme.testing.officer.endeavour;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

public class OfficerEndeavourShowTest extends AcmeEndeavourTest{

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveShow(final int recordIndex, final String executionStart, final String executionEnd, final String duties, final boolean isPublic) {

		super.signIn("officer", "officer");

		super.clickOnMenuFast("Endeavours", "View mine");
		this.sleep(9, false);
		
		super.clickOnListingRecordFast(recordIndex);
		this.sleep(9, false);
		
		

		super.signOut();
	}
}
