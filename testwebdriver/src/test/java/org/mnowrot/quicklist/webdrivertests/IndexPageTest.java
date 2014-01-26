/**
 * 
 */
package org.mnowrot.quicklist.webdrivertests;

import static org.fest.assertions.Assertions.assertThat;

import org.mnowrot.quicklist.webdrivertests.config.BrowserAndUrlProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

/**
 * @author PLMANOW4
 *
 */
public class IndexPageTest {
	
	@Test(dataProvider = "browserAndUrlProvider", dataProviderClass = BrowserAndUrlProvider.class)
	public void shouldShowOneRowInEmptyTableTest(WebDriver browser, String url) {
		// given
		browser.get(url);
		
		// when
		int tableSize = browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr")).size();
		
		// then
		assertThat(tableSize).isEqualTo(1);
	}
	
	@Test(dataProvider = "browserAndUrlProvider", dataProviderClass = BrowserAndUrlProvider.class, 
			dependsOnMethods = {"shouldShowOneRowInEmptyTableTest"})
	public void shutdown(WebDriver browser, String url) {
		browser.quit();
	}
}
