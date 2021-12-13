
package acme.testing.officer.duty;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeEndeavourTest;

public class OfficerDutyListTest extends AcmeEndeavourTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/list-mine.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(4)
	public void positiveList(final int index, final String title, final String description, final String start, final String end, final String workload, final String isPublic, final String link) {

		super.signIn("officer", "officer");

		super.clickOnMenuFast("Duties", "View mine");
		this.sleep(9, false);

		super.checkColumnHasValue(index, 0, title);
		super.checkColumnHasValue(index, 1, description);
		super.checkColumnHasValue(index, 2, start);
		super.checkColumnHasValue(index, 3, end);
		super.checkColumnHasValue(index, 4, workload);
		super.checkColumnHasValue(index, 5, String.valueOf(isPublic));
		super.checkColumnHasValue(index, 6, link);

		super.signOut();
	}
}
