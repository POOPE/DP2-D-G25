package acme.testing.officer.duty;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

public class OfficerDutyUpdateTest extends AcmeEndeavourTest{

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/positive-update.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(44)
	public void positiveUpdate(final int index, final String title, final String description, final String start, final String end, final String workload,final String link) {
		//ignore changing public/private to not interfere with endeavours
		
		super.signIn("officer", "officer");
		
		super.clickOnMenuFast("Duties", "View mine");

		super.clickOnListingRecordFast(index);
		
		if(title != null && !title.isBlank()) super.fillInputBoxIn("title", title);
		if(description != null && !description.isBlank()) super.fillInputBoxIn("description", description);
		if(start != null && !start.isBlank()) super.fillInputBoxIn("executionStart", start);
		if(end != null && !end.isBlank()) super.fillInputBoxIn("executionEnd", end);
		if(workload != null && !workload.isBlank()) super.fillInputBoxIn("workload", workload);
		if(link != null && !link.isBlank()) super.fillInputBoxIn("link", link);


		super.clickOnSubmitButtonFast("Save");
		super.checkNotErrorsExist();
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/negative-update.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(2)
	public void negativeUpdate(final int index, final String title, final String description, final String start, final String end, final String workload,final String link) {
		//ignore changing public/private to not interfere with endeavours
		
		super.signIn("officer", "officer");
		
		super.clickOnMenuFast("Duties", "View mine");

		super.clickOnListingRecordFast(index);
		
		if(title != null && !title.isBlank()) super.fillInputBoxIn("title", title);
		if(title != null && title.equals("%")) super.fillInputBoxIn("title", " ");
		if(description != null && !description.isBlank()) super.fillInputBoxIn("description", description);
		if(description != null && description.equals("%")) super.fillInputBoxIn("description", " ");
		if(start != null && !start.isBlank()) super.fillInputBoxIn("executionStart", start);
		if(start != null && start.equals("%")) super.fillInputBoxIn("executionStart", " ");
		if(end != null && !end.isBlank()) super.fillInputBoxIn("executionEnd", end);
		if(end != null && end.equals("%")) super.fillInputBoxIn("executionEnd", " ");
		if(workload != null && !workload.isBlank()) super.fillInputBoxIn("workload", workload);
		if(workload != null && workload.equals("%")) super.fillInputBoxIn("workload", " ");
		if(link != null && !link.isBlank()) super.fillInputBoxIn("link", link);
		if(link != null && link.equals("%")) super.fillInputBoxIn("link", " ");

		super.clickOnSubmitButtonFast("Save");
		super.checkErrorsExist();
		
		super.signOut();
	}
}
