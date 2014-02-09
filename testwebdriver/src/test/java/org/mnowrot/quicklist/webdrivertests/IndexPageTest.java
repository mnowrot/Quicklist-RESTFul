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
import org.openqa.selenium.interactions.Actions;
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
	@Parameters({ "url" })
	public void setUpUrl(String url) {
		this.url = url;
	}

	@BeforeTest
	@Parameters({ "browser" })
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

	@Test(dependsOnMethods = { "shouldShowOneRowInEmptyTableTest" })
	public void shouldAddListItemOnClickTest() {
		// given
		String itemName = "FirstListItem";
		WebElement newListItemInput = browser.findElement(By.id("newListItemInput"));
		WebElement addNewListItemButton = browser.findElement(By.id("addNewListItemButton"));

		// when
		newListItemInput.sendKeys(itemName);
		addNewListItemButton.click();

		// then
		List<WebElement> newListItemCells = browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr[1]/td"));
		assertThat(newListItemCells).isNotNull();
		assertThat(newListItemCells).hasSize(4);
		assertThat(newListItemCells.get(1).getText()).isEqualTo(itemName);
	}
	
	@Test(dependsOnMethods = { "shouldAddListItemOnClickTest" })
	public void shouldAddListItemOnEnterTest() {
		// given
		String itemName = "SecondListItem";
		WebElement newListItemInput = browser.findElement(By.id("newListItemInput"));

		// when
		newListItemInput.sendKeys(itemName);
		newListItemInput.sendKeys("\n");

		// then
		List<WebElement> newListItemCells = browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr[2]/td"));
		assertThat(newListItemCells).isNotNull();
		assertThat(newListItemCells).hasSize(4);
		assertThat(newListItemCells.get(1).getText()).isEqualTo(itemName);
	}
	
	@Test(dependsOnMethods = { "shouldAddListItemOnEnterTest" })
	public void shouldCancelItemEditionTest() {
		// given
		WebElement editItemButton = browser.findElements(By.id("editItemButton")).get(0);
		WebElement cancelItemEditionButton = browser.findElements(By.id("cancelItemEditionButton")).get(0);
		String itemNameBeforeEdition = browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[2]")).getText();
		WebElement itemEditBox = browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[3]/input"));

		// when
		editItemButton.click();
		itemEditBox.sendKeys("djjdkksksjsjksjsk");
		cancelItemEditionButton.click();

		// then
		String itemNameAfterEdition = browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[2]")).getText();
		assertThat(itemNameBeforeEdition).isEqualTo(itemNameAfterEdition);
	}

	@AfterTest
	public void deleteAllItems() {
		while (browser.findElements(By.id("removeItemButton")).size() > 0) {
			WebElement removeButton = browser.findElement(By.id("removeItemButton"));
			removeButton.click();
			new Actions(browser).pause(1000).perform();
			WebElement confirmRemovalButton = browser.findElement(By.id("confirmItemRemovalButton"));
			confirmRemovalButton.click();
			new Actions(browser).pause(1000).perform();
		}
	}

	@AfterTest
	public void shutdownBrowser() {
		browser.quit();
	}
}
