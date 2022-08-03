package name.bychkov.dlp;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputText;
import org.primefaces.extensions.selenium.component.SelectBooleanCheckbox;

import name.bychkov.junit5.FakeSmtpJUnitExtension;

public class ProfileAddPageTest extends AbstractPrimePageTest {

	@RegisterExtension
	static FakeSmtpJUnitExtension smtp = new FakeSmtpJUnitExtension().port(Integer.valueOf(System.getenv("SMTP_PORT")));

	@Test
	public void testSuccess(ProfileAddPage profileAddPage) throws InterruptedException, MessagingException {
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

		Assertions.assertEquals(1, smtp.getMessages().size());

		assertIsAt("profile-add/profile-approve-requested.xhtml");
	}
}