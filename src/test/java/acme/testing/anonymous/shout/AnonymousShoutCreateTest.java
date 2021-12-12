
package acme.testing.anonymous.shout;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

@Transactional
public class AnonymousShoutCreateTest extends AcmeEndeavourTest {


	@Override
	@BeforeAll
	public void beforeAll() {
		super.setSettings();
		super.populateInitial();
	}
	

	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveCreate(final int recordIndex, final String author, final String text, final String info) {

		super.clickOnMenuFast("Shouts", "Shout!");

		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("link", info);

		super.clickOnSubmitButtonFast("Shout!");
		super.checkNotErrorsExist();
		super.clickOnMenuFast("Shouts", "View shouts");
		
		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, text);
		super.checkColumnHasValue(recordIndex, 3, info);
	}


	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeCreate(final int recordIndex,final String author, final String text, final String info) {
		super.clickOnMenu("Shouts", "Shout!");

		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("link", info);

		super.clickOnSubmitButton("Shout!");
		super.checkErrorsExist();
	}
}
