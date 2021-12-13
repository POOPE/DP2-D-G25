package acme.testing.authenticated.duty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import acme.testing.AcmeEndeavourTest;

public class AuthenticatedDutyListTest extends AcmeEndeavourTest{

	@Test
	public void positiveListFinishedPublic() {
		super.signIn("officer", "officer");
		super.clickOnMenu("Duties", "View duties");

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		
		for(int i=0;i<3;i++) {
			final String dateStr = super.getColumnValue(i, 3);
			Assert.isTrue(LocalDateTime.parse(dateStr, formatter).isBefore(LocalDateTime.now()),"Duty is not finished");
		}

		this.signOut();
	}
}
