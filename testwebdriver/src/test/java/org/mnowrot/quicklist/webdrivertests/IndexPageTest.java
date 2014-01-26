/**
 * 
 */
package org.mnowrot.quicklist.webdrivertests;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mnowrot.quicklist.webdrivertests.config.BrowserAndUrlConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author PLMANOW4
 *
 */
@RunWith(Parameterized.class)
public class IndexPageTest {
	
	private static List<Object[]> configuration;
	
	@Parameters
	public static List<Object[]> browserandUrlConfigurations() {
		configuration = BrowserAndUrlConfiguration.getConfiguration();
		return configuration;
	}
	
	private WebDriver browser;
	private String url;

	public IndexPageTest(WebDriver browser, String url) {
		this.browser = browser;
		this.url = url;
	}

	@Test
	public void shouldShowOneRowInEmptyTableTest() {
		// given
		browser.get(url);
		
		// when
		int tableSize = browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr")).size();
		
		// then
		assertThat(tableSize).isEqualTo(1);
	}
	
	@AfterClass
	public static void shutdown() {
		if(configuration != null) {
			for (Object[] configItem : configuration) {
				((WebDriver) configItem[0]).quit();
			}
		}
	}
}
