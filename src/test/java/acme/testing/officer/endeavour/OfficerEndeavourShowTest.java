package acme.testing.officer.endeavour;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmeEndeavourTest;

public class OfficerEndeavourShowTest extends AcmeEndeavourTest{

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(7)
	public void positiveShow(final int recordIndex, final String executionStart, final String executionEnd, final String duties, final boolean isPublic,  final int endeavour) {

		super.signIn("officer", "officer");

		super.clickOnMenuFast("Endeavours", "View mine");
		this.sleep(9, false);
		
		super.clickOnListingRecordFast(recordIndex);
		this.sleep(9, false);
		
		super.checkInputBoxHasValue("executionStart", executionStart);
		super.checkInputBoxHasValue("executionEnd", executionEnd);
		
		if (duties != null && !duties.isBlank()) {
			final List<String> d = Arrays.asList(duties.split(","));
			for (final String str : d) {
				super.checkExists(By.xpath(String.format("//div[@id='binded_duties_collection']/div/label[@id='%s']", str)));
			}
		}
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(8)
	public void negativeShow(final int recordIndex, final String executionStart, final String executionEnd, final String duties, final boolean isPublic,  final int endeavour) {
		super.navigate("/officer/endeavour/show", String.format("id=%d\",endeavour)",endeavour));
		super.checkPanicExists();	
	}
}
