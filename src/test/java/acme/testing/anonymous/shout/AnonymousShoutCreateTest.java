
package acme.testing.anonymous.shout;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

@Transactional
public class AnonymousShoutCreateTest extends AcmeEndeavourTest {



	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveCreate(final String author, final String text, final String info) {

		super.clickOnMenu("Anonymous", "Create shout");

		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);

		super.clickOnSubmitButton("Shout!");
		super.checkNotErrorsExist();
		super.clickOnMenu("Anonymous", "List shouts");
		super.checkColumnHasValue(0, 1, author);
		super.checkColumnHasValue(0, 2, text);
		super.checkColumnHasValue(0, 3, info);
	}


	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeCreate(final String author, final String text, final String info) {
		super.clickOnMenu("Anonymous", "Create shout");

		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);

		super.clickOnSubmitButton("Shout!");
		super.checkErrorsExist();
	}
}
