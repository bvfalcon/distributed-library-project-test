package name.bychkov.dlp;

import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputText;
import org.primefaces.extensions.selenium.component.SelectBooleanCheckbox;

import lombok.Getter;

public class ProfileAddPage extends AbstractPrimePage {

	@FindBy(id = "form:email")
	@Getter
	private InputText email;
	
	@FindBy(id = "form:name")
	@Getter
	private InputText name;
	
	@FindBy(id = "form:password")
	@Getter
	private InputText password;
	
	@FindBy(id = "form:repeatPassword")
	@Getter
	private InputText repeatPassword;
	
	@FindBy(id = "form:agreementAccepted")
	@Getter
	private SelectBooleanCheckbox agreementAccepted;
	
	@FindBy(id = "form:save")
	@Getter
	private CommandButton save;
	
	@Override
	public String getLocation() {
		return "profile-add/profile-add.xhtml";
	}
}