
package acme.testing.anonymous.shout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

@Transactional
public class AnonymousShoutListTest extends AcmeEndeavourTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/list-shouts.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveShoutListing(final int recordIndex, final String moment, final String author, final String text, final String info) {
		super.clickOnMenu("Shouts", "View shouts");

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

		if (LocalDateTime.parse(moment, formatter).isBefore(LocalDateTime.now())) {
			super.checkColumnHasValue(recordIndex, 0, moment);
			super.checkColumnHasValue(recordIndex, 1, author);
			super.checkColumnHasValue(recordIndex, 2, text);
			super.checkColumnHasValue(recordIndex, 3, info);
		}
	}

	@Test
	public void negativeShoutListing() {
		super.signIn("administrator", "administrator");
		super.navigate("/anonymous/shout/list", "");
		this.checkPanicExists();
		this.signOut();

	}
}
