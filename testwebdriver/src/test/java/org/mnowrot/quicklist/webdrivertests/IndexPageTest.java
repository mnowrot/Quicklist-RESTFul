/**
 * 
 */
package org.mnowrot.quicklist.webdrivertests;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.mnowrot.quicklist.webdrivertests.config.BrowserAndUrlProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
	public void shouldAddListItemOnClickTest(WebDriver browser, String url) {
		// given
		String itemName = "FirstListItem";
		WebElement newListItemInput = browser.findElement(By.id("newListItemInput"));
		WebElement addNewListItemButton = browser.findElement(By.id("addNewListItemButton"));
		
		// when
		newListItemInput.sendKeys(itemName);
		addNewListItemButton.click();
		
		// then
		List<WebElement> newListItemCells = browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr/td"));
		assertThat(newListItemCells).isNotNull();
		assertThat(newListItemCells).hasSize(3);
		assertThat(newListItemCells.get(1).getText()).isEqualTo(itemName);
	}
	
	@Test(dataProvider = "browserAndUrlProvider", dataProviderClass = BrowserAndUrlProvider.class, 
			dependsOnMethods = {"shouldAddListItemOnClickTest"})
	public void shutdown(WebDriver browser, String url) {
		browser.quit();
	}
}
