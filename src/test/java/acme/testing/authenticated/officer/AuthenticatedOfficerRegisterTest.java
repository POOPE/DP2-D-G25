
package acme.testing.authenticated.officer;

import org.junit.jupiter.api.Test;

import acme.testing.AcmeEndeavourTest;

public class AuthenticatedOfficerRegisterTest extends AcmeEndeavourTest {

	/**
	 * 
	 * Coverage: 67.5%
	 * 
	 * Sign up as user, use different user for each test. Try to become manager with data provided
	 * 
	 * @param recordIndex
	 * @param company
	 * @param department
	 */
	@Test
	public void becomeManagerPositive() {
		super.signUp("user_positive_test", "testtest", "testname", "testsurname", "testemail@acme.com");
		super.signIn("user_positive_test", "testtest");
		super.clickOnMenuFast("Account", "Become a manager");

		super.clickOnSubmitButtonFast("Register");

		super.checkNotErrorsExist();

		super.signOut();
	}

}
