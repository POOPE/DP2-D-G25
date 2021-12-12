package acme.testing.officer.endeavour;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmeEndeavourTest;

public class OfficerEndeavourCreateTest extends AcmeEndeavourTest{

	
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveCreate(final int recordIndex, final String executionStart, final String executionEnd, final String duties, final boolean suggest) {

		super.clickOnMenuFast("Endeavours", "Create endeavour");

		super.fillInputBoxIn("executionStart", executionStart);
		super.fillInputBoxIn("executionEnd", executionEnd);
		if(duties != null && !duties.isBlank()) {
			final List<String> d = Arrays.asList(duties.split(","));
			for(final String str : d) {
				final By locator = By.xpath(String.format("//*[@id='{}']",str));
				super.clickFast(locator);
			}
		}
		if(suggest) {
			final By locator = By.xpath("//*[@id='btn-suggest-execperiod']");
			super.clickFast(locator);
		}

		super.clickOnSubmitButtonFast("Save");
		super.checkNotErrorsExist();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeCreate(final int recordIndex, final String executionStart, final String executionEnd, final String duties, final boolean suggest) {

		super.clickOnMenuFast("Endeavours", "Create endeavour");

		super.fillInputBoxIn("executionStart", executionStart);
		super.fillInputBoxIn("executionEnd", executionEnd);
		if(duties != null && !duties.isBlank()) {
			final List<String> d = Arrays.asList(duties.split(","));
			for(final String str : d) {
				final By locator = By.xpath(String.format("//*[@id='{}']",str));
				super.clickFast(locator);
			}
		}
		if(suggest) {
			final By locator = By.xpath("//*[@id='btn-suggest-execperiod']");
			super.clickFast(locator);
		}

		super.clickOnSubmitButtonFast("Save");
		super.checkErrorsExist();
	}
}
