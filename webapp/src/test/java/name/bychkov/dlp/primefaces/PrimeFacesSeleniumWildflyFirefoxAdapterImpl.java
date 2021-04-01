package name.bychkov.dlp.primefaces;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class PrimeFacesSeleniumWildflyFirefoxAdapterImpl extends PrimeFacesSeleniumWildflyAdapter
{
	@Override
	public WebDriver createWebDriver()
	{
		FirefoxOptions options = new FirefoxOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.setHeadless(true);
		return new FirefoxDriver(options);
	}
}