package name.bychkov.dlp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputText;
import org.primefaces.extensions.selenium.component.SelectBooleanCheckbox;

import com.nilhcem.fakesmtp.FakeSMTP;

import name.bychkov.fakesmtp.FakeSmtpJUnitExcension;

public class ProfileAddPageTest extends AbstractPrimePageTest {

	@RegisterExtension
	static FakeSmtpJUnitExcension s = new FakeSmtpJUnitExcension().port(Integer.valueOf(System.getenv("SMTP_PORT")));

	@Test
	public void testSuccess(ProfileAddPage profileAddPage) throws InterruptedException {
		Thread.sleep(2000);
		Assertions.assertTrue(profileAddPage.isAt());

		InputText email = profileAddPage.getEmail();
		email.setValue("example-user@example.com");

		InputText name = profileAddPage.getName();
		name.setValue("Example User");

		InputText password = profileAddPage.getPassword();
		password.setValue("test");

		InputText repeatedPassword = profileAddPage.getRepeatPassword();
		repeatedPassword.setValue("test");

		SelectBooleanCheckbox agreement = profileAddPage.getAgreementAccepted();
		agreement.check();

		CommandButton button = profileAddPage.getSave();
		button.click();

		Assertions.assertEquals(1, FakeSMTP.getEmails().size());

		assertIsAt("profile-add/profile-approve-requested.xhtml");
	}
}