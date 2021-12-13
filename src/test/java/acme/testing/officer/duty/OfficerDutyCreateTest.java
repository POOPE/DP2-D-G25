package acme.testing.officer.duty;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

public class OfficerDutyCreateTest extends AcmeEndeavourTest{

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/positive-create.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(1)
	public void positiveCreate(final int index, final String title, final String description, final String start, final String end, final String workload) {

		super.signIn("officer", "officer");
		
		super.clickOnMenuFast("Duties", "Create duty");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("executionStart", start);
		super.fillInputBoxIn("executionEnd", end);
		super.fillInputBoxIn("workload", workload);

		super.clickOnSubmitButtonFast("Create");
		super.checkNotErrorsExist();
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/negative-create.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(1)
	public void negativeCreate(final int index, final String title, final String description, final String start, final String end, final String workload) {

		super.signIn("officer", "officer");
		
		super.clickOnMenuFast("Duties", "Create duty");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("executionStart", start);
		super.fillInputBoxIn("executionEnd", end);
		super.fillInputBoxIn("workload", workload);

		super.clickOnSubmitButtonFast("Create");
		super.checkErrorsExist();
		
		super.signOut();
	}
}
