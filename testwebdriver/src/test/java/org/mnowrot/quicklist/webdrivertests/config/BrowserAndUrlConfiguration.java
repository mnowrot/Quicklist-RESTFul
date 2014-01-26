/**
 * 
 */
package org.mnowrot.quicklist.webdrivertests.config;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @author PLMANOW4
 *
 */
public class BrowserAndUrlConfiguration {

	public static List<Object[]> getConfiguration() {
		//browsers
		WebDriver firefox = new FirefoxDriver();
		WebDriver chrome = new ChromeDriver();
		WebDriver internetExplorer = new InternetExplorerDriver();
		
		//urls - add more for different technologies
		String serverUrl = "http://localhost:8080";
		String angularJsUrl = serverUrl + "/quicklist-angularjs";
				
		return Arrays.asList(
				// Urls to be tested with Firefox
				new Object[] {firefox, angularJsUrl},
				
				// Urls to be tested with Chrome
				new Object[] {chrome, angularJsUrl},
				
				// Urls to be tested with InternetExplorer
				new Object[] {internetExplorer, angularJsUrl});
	}

}
