
package acme.testing;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acme.framework.helpers.StringHelper;

public abstract class AcmeEndeavourTest extends AcmeTest {

	//defaults are 20 and 50
	public static int		SHORT_SLEEP		= 9;
	public static int		LONG_SLEEP		= 20;
	public static boolean	SKIP_POPULATE	= true;


	@Override
	@BeforeAll
	public void beforeAll() {
		super.setAutoPausing(false);
		super.setDefaultTimeout(10);
		super.setHeadless(false);
		super.beforeAll();

		super.setBaseCamp("http", "localhost", "8080", "/Acme-Endeavours", "/master/welcome", "?language=en&debug=true");

		if (!AcmeEndeavourTest.SKIP_POPULATE) {
			this.navigateHome();
			this.signIn("administrator", "administrator");
			super.clickOnMenu("Administrator", "Populate DB (samples)");
			this.checkAlertExists(true);
			this.signOut();
		}else {
			//just for testing
			this.signIn("administrator", "administrator");
			this.signOut();
		}
	}


	protected void signIn(final String username, final String password) {
		assert !StringHelper.isBlank(username);
		assert !StringHelper.isBlank(password);

		super.navigateHome();
		super.clickOnMenu("Sign in", null);
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("remember", "true");
		super.clickOnSubmitButton("Sign in");
		super.checkSimplePath("/master/welcome");
		super.checkLinkExists("Account");
	}

	protected void signOut() {
		super.navigateHome();
		super.clickOnMenu("Sign out", null);
		super.checkSimplePath("/master/welcome");
	}

	protected void signUp(final String username, final String password, final String name, final String surname, final String email) {
		assert !StringHelper.isBlank(username);
		assert !StringHelper.isBlank(password);
		assert !StringHelper.isBlank(name);
		assert !StringHelper.isBlank(surname);
		assert !StringHelper.isBlank(email);

		super.navigateHome();
		super.clickOnMenu("Sign up", null);
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("confirmation", password);
		super.fillInputBoxIn("identity.name", name);
		super.fillInputBoxIn("identity.email", email);
		super.fillInputBoxIn("identity.surname", surname);
		super.fillInputBoxIn("accept", "true");
		super.clickOnSubmitButton("Sign up");
		super.checkSimplePath("/master/welcome");
	}

	@Override
	protected void checkNotErrorsExist() {
		final List<WebElement> errors = this.driver.findElements(By.className("text-danger"));
		assert errors.isEmpty() : "No errors were expected in current form";
	}

	protected void checkNotElementsExist(final By locator) {
		final List<WebElement> elements = this.driver.findElements(locator);
		assert elements.isEmpty();
	}

	public void click(final By locator) {
		assert locator != null;
		WebElement element;
		element = this.locateOne(locator);
		element.click();
		this.sleep(9, false);
	}

	@Override
	protected void sleep(final int duration, final boolean exact) {
		assert duration >= 0 && duration <= 3600;

		long autoPause;

		try {
			if (exact) {
				Awaitility.await().atMost(Duration.of(100L * duration, ChronoUnit.MILLIS));
			} else if (this.autoPausing) {
				autoPause = 100L * (1 + this.random.nextInt(duration));
				Awaitility.await().atMost(Duration.of(100L * autoPause, ChronoUnit.MILLIS));
			}
		} catch (final Throwable oops) {
			throw new RuntimeException(oops);
		}
	}

	@Override
	protected void shortSleep() {
		this.sleep(AcmeEndeavourTest.SHORT_SLEEP, false);
	}

	@Override
	protected void longSleep() {
		this.sleep(AcmeEndeavourTest.LONG_SLEEP, false);
	}
	
//
//	public void clickOnMenuById(final String headerId, final String optionId) {
//		assert !StringHelper.isBlank(headerId);
//		assert optionId == null || !StringHelper.isBlank(optionId);
//
//		By toggleLocator, headerLocator, optionLocator;
//		WebElement toggle;
//		String ariaExpanded;
//
//		try {
//			toggleLocator = By.xpath("//button[@class='navbar-toggler']");
//			toggle = super.locateOne(toggleLocator);
//			if (toggle.isDisplayed()) {
//				ariaExpanded = toggle.getAttribute("aria-expanded");
//				if (ariaExpanded == null)
//					super.clickAndGo(toggle);
//			}
//		} catch (final Throwable oops) {
//			// INFO: Can silently ignore the exception here.
//			// INFO+ Sometimes, the toggle gets stale unexpectedly.
//			//oops.printStackTrace();
//		}
//
//		headerLocator = By.xpath(String.format("//a[@id='%s']", headerId));
//		if (optionId == null)
//			super.clickAndWait(headerLocator);
//		else {
//			try {
//				super.clickAndGo(headerLocator);
//			} catch (final Throwable oops) {
//				// INFO: Can silently ignore the exception here.
//				// INFO+ Sometimes, the toggle gets stale unexpectedly
//				// INFO+ and that has an impact on the main menu.
//				//oops.printStackTrace();
//			} 
//			optionLocator = By.xpath(String.format("//a[@id='%s']", optionId));
//			super.clickAndWait(optionLocator);
//		}
//	}
	


}
