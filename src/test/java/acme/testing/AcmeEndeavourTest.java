
package acme.testing;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acme.framework.helpers.PerformanceFileHelper;
import acme.framework.helpers.StringHelper;

public abstract class AcmeEndeavourTest extends AcmeTest {

	//defaults are 20 and 50
	public static int		SHORT_SLEEP		= 9;
	public static int		LONG_SLEEP		= 20;
	public static boolean	SKIP_POPULATE	= false;
	public static boolean	HEADLESS	= false;
	public static boolean	AUTO_PAUSE	= false;
	public static int	TIMEOUT	= 19;


	@Override
	@BeforeAll
	public void beforeAll() {
		this.setSettings();
		if (!AcmeEndeavourTest.SKIP_POPULATE) {
			this.populateSamples();
		}
	}
	
	public void setSettings() {
		super.setAutoPausing(AcmeEndeavourTest.AUTO_PAUSE);
		super.setDefaultTimeout(AcmeEndeavourTest.TIMEOUT);
		super.setHeadless(AcmeEndeavourTest.HEADLESS);
		super.beforeAll();

		super.setBaseCamp("http", "localhost", "8080", "/Acme-Endeavours", "/master/welcome", "?language=en&debug=true");
		
	}
	
	public void populateSamples() {	
			this.navigateHome();
			this.signIn("administrator", "administrator");
			super.clickOnMenu("Administrator", "Populate DB (samples)");
			this.checkAlertExists(true);
			this.signOut();
	}
	
	public void populateInitial() {	
		this.navigateHome();
		this.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "Populate DB (initial)");
		this.checkAlertExists(true);
		this.signOut();
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
	
	
	protected void clickOnMenuFast(final String header, final String option) {
		assert !StringHelper.isBlank(header);
		assert option == null || !StringHelper.isBlank(option);

		By toggleLocator, headerLocator, optionLocator;
		WebElement toggle;
		String ariaExpanded;

		try {
			toggleLocator = By.xpath("//button[@class='navbar-toggler']");
			toggle = super.locateOne(toggleLocator);
			if (toggle.isDisplayed()) {
				ariaExpanded = toggle.getAttribute("aria-expanded");
				if (ariaExpanded == null)
					this.clickFast(toggle);
			}
		} catch (final Throwable oops) {
			// INFO: Can silently ignore the exception here.
			// INFO+ Sometimes, the toggle gets stale unexpectedly.
			//oops.printStackTrace();
		}

		headerLocator = By.xpath(String.format("//div[@id='mainMenu']/ul/li/a[normalize-space()='%s']", header));
		if (option == null)
			super.clickAndGo(headerLocator);
		else {
			try {
				this.clickFast(headerLocator);
			} catch (final Throwable oops) {
				// INFO: Can silently ignore the exception here.
				// INFO+ Sometimes, the toggle gets stale unexpectedly
				// INFO+ and that has an impact on the main menu.
				//oops.printStackTrace();
			} 
			optionLocator = By.xpath(String.format("//div[@id='mainMenu']/ul/li[a[normalize-space()='%s']]/div[contains(@class, 'dropdown-menu')]/a[normalize-space()='%s']", header, option));
			this.clickFast(optionLocator);
		}
	}
	
	
	protected void clickFast(final By locator) {
		assert locator != null;

		WebElement element;

		element = this.locateOne(locator);
		this.clickFast(element);
	}

	protected void clickFast(final WebElement element) {
		assert element != null;

		// INFO: WebElement::click is a nightmare.  Don't use it!
		this.executor.executeScript("arguments[0].click();", element);		
		this.sleep(12, false);
	}
	

	protected void clickOnSubmitButtonFast(final String label) {
		assert !StringHelper.isBlank(label);

		By locator;

		locator = By.xpath(String.format("//button[@type='submit' and normalize-space()='%s']", label));
		this.clickFast(locator);
	}
	
	protected void clickOnListingRecordFast(final int recordIndex) {
		assert recordIndex >= 0;

		List<WebElement> record;
		WebElement column;

		record = this.getListingRecord(recordIndex);
		column = record.get(1);
		this.clickNavigateFast(column);
	}
	
	protected void clickNavigateFast(final WebElement element) {
		assert element != null;

		this.navigateFast(() -> this.executor.executeScript("arguments[0].click();", element));
	}
	
	protected void navigateFast(final Runnable navigator) {
		assert navigator != null;

		By locator;
		WebElement html;
		long startTime, endTime, duration;
		String simplePath;

		locator = By.tagName("html");
		html = this.driver.findElement(locator);
		assert html != null;

		startTime = System.currentTimeMillis();
		navigator.run();
		this.sleep(12, false);
		endTime = System.currentTimeMillis();
		duration = endTime - startTime;
		simplePath = this.getSimplePath();
				 
		PerformanceFileHelper.writeRequestRecord(simplePath, duration);

		this.sleep(21,false);
	}

}
