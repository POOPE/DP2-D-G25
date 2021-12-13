
package acme.testing.officer.endeavour;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

public class OfficerEndeavourListTest extends AcmeEndeavourTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(4)
	public void positiveList(final int recordIndex, final String executionStart, final String executionEnd, final String duties, final boolean isPublic, final int endeavour, final String workload) {

		super.signIn("officer", "officer");

		super.clickOnMenuFast("Endeavours", "View mine");
		this.sleep(9, false);

		super.checkColumnHasValue(recordIndex, 0, executionStart);
		super.checkColumnHasValue(recordIndex, 1, executionEnd);
		super.checkColumnHasValue(recordIndex, 2, workload);
		super.checkColumnHasValue(recordIndex, 3, String.valueOf(isPublic));
		
		super.signOut();
	}

}
