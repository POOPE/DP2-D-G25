
package acme.testing.officer.endeavour;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmeEndeavourTest;

public class OfficerEndeavourPublishTest extends AcmeEndeavourTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positivePublish(final int recordIndex, final String executionStart, final String executionEnd, final String duties, final boolean suggest, final String removeDuties) {

		super.signIn("officer", "officer");
		
		super.clickOnMenuFast("Endeavours", "Create endeavour");

		super.fillInputBoxIn("executionStart", executionStart);
		super.fillInputBoxIn("executionEnd", executionEnd);
		if (duties != null && !duties.isBlank()) {
			final List<String> d = Arrays.asList(duties.split(","));
			for (final String str : d) {
				final By locator = By.xpath(String.format("//*[@id='%s']", str));
				super.clickFast(locator);
			}
			
			super.clickFast(By.xpath("//*[@id='btn-bind-selected']"));
		}
		if (suggest) {
			final By locator = By.xpath("//*[@id='btn-suggest-execperiod']");
			super.clickFast(locator);
		}

		super.clickOnSubmitButtonFast("Create");
		
		super.clickOnMenuFast("Endeavours", "View mine");
		//population for endeavours for officer is two, its simpler this way
		super.clickFast(By.xpath(String.format("//table[@id='list']/tbody/tr[%d]/td", 2 + recordIndex)));
		

		if (removeDuties != null && !removeDuties.isBlank()) {
			final List<String> d = Arrays.asList(removeDuties.split(","));
			for (final String str : d) {
				final By locator = By.xpath(String.format("//*[@id='{}']", str));
				super.clickFast(locator);
			}
			super.clickFast(By.xpath("//*[@id='btn-unbind-selected']"));
		}
		if(suggest) {
			final By locator = By.xpath("//*[@id='btn-suggest-execperiod']");
			super.clickFast(locator);
		}
		
		super.clickOnSubmitButtonFast("Save & publish");
		super.checkNotErrorsExist();
		
		super.signOut();
	}

//	@ParameterizedTest
//	@CsvFileSource(resources = "/officer/endeavour/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
//	@Order(20)
//	public void negativePublish(final int recordIndex, final String executionStart, final String executionEnd, final String duties, final boolean suggest) {
//
//		super.clickOnMenuFast("Endeavours", "Create endeavour");
//
//		super.fillInputBoxIn("executionStart", executionStart);
//		super.fillInputBoxIn("executionEnd", executionEnd);
//		if (duties != null && !duties.isBlank()) {
//			final List<String> d = Arrays.asList(duties.split(","));
//			for (final String str : d) {
//				final By locator = By.xpath(String.format("//*[@id='{}']", str));
//				super.clickFast(locator);
//			}
//		}
//		if (suggest) {
//			final By locator = By.xpath("//*[@id='btn-suggest-execperiod']");
//			super.clickFast(locator);
//		}
//
//		super.clickOnSubmitButtonFast("Save");
//		super.checkErrorsExist();
//	}
}
