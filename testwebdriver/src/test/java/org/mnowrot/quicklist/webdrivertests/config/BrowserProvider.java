/**
 * 
 */
package org.mnowrot.quicklist.webdrivertests.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @author PLMANOW4
 *
 */
public class BrowserProvider {

	public static WebDriver provideBrowserByName(String browserName) {
		switch(browserName) {
		case "firefox":
			return new FirefoxDriver();
		case "chrome":
			return new ChromeDriver();
		default:
			return new InternetExplorerDriver(); 
		}
	}

}
