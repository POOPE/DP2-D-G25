
package acme.testing.officer.endeavour;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmeEndeavourTest;

@Transactional
public class OfficerEndeavourCreateTest extends AcmeEndeavourTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(1)
	public void positiveCreate(final int recordIndex, final String executionStart, final String executionEnd, final String duties, final boolean suggest) {

		super.signIn("officer", "officer");
		
		super.clickOnMenuFast("Endeavours", "Create endeavour");

		super.fillInputBoxIn("executionStart", executionStart);
		super.fillInputBoxIn("executionEnd", executionEnd);
		if (duties != null && !duties.isBlank()) {
			final List<String> d = Arrays.asList(duties.split(","));
			for (final String str : d) {
				super.clickFast(By.xpath(String.format("//*[@id='%s']", str)));
			}
			super.clickFast(By.xpath("//*[@id='btn-bind-selected']"));
		}
		if (suggest) {
			super.clickFast(By.xpath("//*[@id='btn-suggest-execperiod']"));
		}

		super.clickOnSubmitButtonFast("Create");
		super.checkNotErrorsExist();
		
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/endeavour/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(2)
	public void negativeCreate(final int recordIndex, final String executionStart, final String executionEnd, final String duties, final boolean suggest) {

		super.signIn("officer", "officer");
		
		super.clickOnMenuFast("Endeavours", "Create endeavour");

		super.fillInputBoxIn("executionStart", executionStart);
		super.fillInputBoxIn("executionEnd", executionEnd);
		if (duties != null && !duties.isBlank()) {
			final List<String> d = Arrays.asList(duties.split(","));
			for (final String str : d) {
				super.clickFast(By.xpath(String.format("//*[@id='%s']", str)));
			}
			super.clickFast(By.xpath("//*[@id='btn-bind-selected']"));
		}
		if (suggest) {
			super.clickFast(By.xpath("//*[@id='btn-suggest-execperiod']"));
		}

		super.clickOnSubmitButtonFast("Create");
		super.checkErrorsExist();
		
		super.signOut();
	}
}
