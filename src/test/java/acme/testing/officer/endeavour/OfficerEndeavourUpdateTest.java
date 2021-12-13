
package acme.testing.officer.endeavour;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmeEndeavourTest;

public class OfficerEndeavourUpdateTest extends AcmeEndeavourTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(9)
	public void positiveUpdate(final int recordIndex, final int updatingIndex, final String executionStart, final String executionEnd, final String duties, final boolean suggest, final String removeDuties) {

		super.signIn("officer", "officer");

		super.clickOnMenuFast("Endeavours", "View mine");
		this.sleep(9, false);
		//population for endeavours for officer is two, its simpler this way
		super.clickOnListingRecordFast(updatingIndex);

		if (executionStart!=null && !executionStart.isBlank()) {
			super.fillInputBoxIn("executionStart", executionStart);
		}
		if (executionEnd!=null && !executionEnd.isBlank()) {
			super.fillInputBoxIn("executionEnd", executionEnd);
		}

		if (duties != null && !duties.isBlank()) {
			final List<String> d = Arrays.asList(duties.split(","));
			for (final String str : d) {
				final By locator = By.xpath(String.format("//*[@id='%s']", str));
				super.clickFast(locator);
			}

			super.clickFast(By.xpath("//*[@id='btn-bind-selected']"));
		}
		
		if (removeDuties != null && !removeDuties.isBlank()) {
			final List<String> d = Arrays.asList(removeDuties.split(","));
			for (final String str : d) {
				final By locator = By.xpath(String.format("//*[@id='%s']", str));
				super.clickFast(locator);
			}

			super.clickFast(By.xpath("//*[@id='btn-unbind-selected']"));
		}
		
		if (suggest) {
			final By locator = By.xpath("//*[@id='btn-suggest-execperiod']");
			super.clickFast(locator);
		}
		
		

		super.clickOnSubmitButtonFast("Save");
		super.checkNotErrorsExist();

		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeUpdate(final int recordIndex, final int updatingIndex, final String executionStart, final String executionEnd, final String duties, final boolean suggest, final String removeDuties) {

		super.signIn("officer", "officer");

		super.clickOnMenuFast("Endeavours", "View mine");
		this.sleep(9, false);
		//population for endeavours for officer is two, its simpler this way
		super.clickOnListingRecordFast(updatingIndex);

		if (executionStart!=null && !executionStart.isBlank()) {
			super.fillInputBoxIn("executionStart", executionStart);
		}else if(executionStart!=null && executionStart.equals("%")) {
			super.fillInputBoxIn("executionStart", "");
		}
		if (executionEnd!=null && !executionEnd.isBlank()) {
			super.fillInputBoxIn("executionEnd", executionEnd);
		}else if(executionEnd!=null && executionEnd.equals("%")) {
			super.fillInputBoxIn("executionEnd", "");
		}

		if (duties != null && !duties.isBlank()) {
			final List<String> d = Arrays.asList(duties.split(","));
			for (final String str : d) {
				final By locator = By.xpath(String.format("//*[@id='%s']", str));
				super.clickFast(locator);
			}

			super.clickFast(By.xpath("//*[@id='btn-bind-selected']"));
		}
		
		if (removeDuties != null && !removeDuties.isBlank()) {
			final List<String> d = Arrays.asList(removeDuties.split(","));
			for (final String str : d) {
				final By locator = By.xpath(String.format("//*[@id='%s']", str));
				super.clickFast(locator);
			}

			super.clickFast(By.xpath("//*[@id='btn-unbind-selected']"));
		}
		
		if (suggest) {
			final By locator = By.xpath("//*[@id='btn-suggest-execperiod']");
			super.clickFast(locator);
		}
		
		

		super.clickOnSubmitButtonFast("Save");
		super.checkErrorsExist();

		super.signOut();
	}
}
