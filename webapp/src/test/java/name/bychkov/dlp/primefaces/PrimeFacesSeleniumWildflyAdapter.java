package name.bychkov.dlp.primefaces;

import org.openqa.selenium.WebDriver;
import org.primefaces.extensions.selenium.spi.PrimeSeleniumAdapter;

public abstract class PrimeFacesSeleniumWildflyAdapter implements PrimeSeleniumAdapter
{
	@Override
	public abstract WebDriver createWebDriver();

      	@Override
	public String getBaseUrl()
	{
		return "http://localhost:8080/dlp-dev/";
	}

	@Override
	public void startup() throws Exception
	{
	}
	
	@Override
	public void shutdown()
	{
	}
}