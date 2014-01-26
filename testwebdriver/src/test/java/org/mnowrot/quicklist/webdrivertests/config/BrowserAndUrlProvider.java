/**
 * 
 */
package org.mnowrot.quicklist.webdrivertests.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.DataProvider;

/**
 * @author PLMANOW4
 *
 */
public class BrowserAndUrlProvider {
	
	//browsers
	private static WebDriver firefox = new FirefoxDriver();
	private static WebDriver chrome = new ChromeDriver();
	private static WebDriver internetExplorer = new InternetExplorerDriver();
	
	//urls - add more for different technologies
	private static String serverUrl = "http://localhost:8080";
	private static String angularJsUrl = serverUrl + "/quicklist-angularjs";

	@DataProvider(name="browserAndUrlProvider")
	public static Object[][] provideBrowserAndUrl() {
		return new Object[][] {
				// Urls to be tested with Firefox
				new Object[] {firefox, angularJsUrl},
				
				// Urls to be tested with Chrome
				new Object[] {chrome, angularJsUrl},
				
				// Urls to be tested with InternetExplorer
				new Object[] {internetExplorer, angularJsUrl}
		};
	}

}
