package name.bychkov.dlp.primefaces;

import org.primefaces.selenium.spi.DeploymentAdapter;

public class PrimeFacesSeleniumWildflyAdapter implements DeploymentAdapter
{
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