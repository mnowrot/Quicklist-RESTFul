/**
 * 
 */
package org.mnowrot.quicklist.webdrivertests;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.mnowrot.quicklist.webdrivertests.config.BrowserProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @author PLMANOW4
 *
 */
public class IndexPageTest {
	
	private String url;
	private WebDriver browser;
	
	@BeforeTest
	@Parameters({"url"})
	public void setUpUrl(String url) {
		this.url = url;
	}
	
	@BeforeTest
	@Parameters({"browser"})
	public void setUpBrowser(String browserName) {
		this.browser = BrowserProvider.provideBrowserByName(browserName);
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
	
	@Test(dependsOnMethods = {"shouldShowOneRowInEmptyTableTest"})
	public void shouldAddListItemOnClickTest() {
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
		assertThat(newListItemCells).hasSize(4);
		assertThat(newListItemCells.get(1).getText()).isEqualTo(itemName);
	}
	
	@AfterTest
	public void shutdownBrowser() {
		browser.quit();
	}
}
