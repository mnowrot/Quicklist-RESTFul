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

	private WebDriver browser;

	@BeforeTest
	@Parameters({ "url", "browser" })
	public void prepareBrowserAndPage(String url, String browserName) {
		this.browser = BrowserProvider.provideBrowserByName(browserName);
		browser.manage().deleteAllCookies();
		browser.get(url);
		deleteAllItems();
	}

	@Test
	public void shouldShowOneRowInEmptyTableTest() {
		// given

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
		assertThat(itemNameAfterEdition).isEqualTo(itemNameBeforeEdition);
	}
	
	@Test(dependsOnMethods = { "shouldCancelItemEditionTest" })
	public void shouldEditItemOnClickTest() {
		// given
		WebElement editItemButton = browser.findElements(By.id("editItemButton")).get(0);
		WebElement saveEditedItemButton = browser.findElements(By.id("saveEditedItemButton")).get(0);
		String editedItemText = "Edited FirstListItem";
		WebElement itemEditBox = browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[3]/input"));

		// when
		editItemButton.click();
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		saveEditedItemButton.click();

		// then
		String itemNameAfterEdition = browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[2]")).getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}
	
	@Test(dependsOnMethods = { "shouldEditItemOnClickTest" })
	public void shouldEditItemOnEnterTest() {
		// given
		WebElement editItemButton = browser.findElements(By.id("editItemButton")).get(1);
		String editedItemText = "Edited SecondListItem";
		WebElement itemEditBox = browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[2]/td[3]/input"));

		// when
		editItemButton.click();
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		itemEditBox.sendKeys("\n");

		// then
		String itemNameAfterEdition = browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[2]/td[2]")).getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}
	
	@Test(dependsOnMethods = { "shouldEditItemOnEnterTest" })
	public void shouldInplaceEditItemTest() {
		// given
		String editedItemText = "Inplace edited FirstListItem";
		WebElement itemEditText = browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[2]"));
		WebElement itemEditBox = browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[3]/input"));

		// when
		new Actions(browser).doubleClick(itemEditText).perform();
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		itemEditBox.sendKeys("\n");

		// then
		itemEditText = browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[2]"));
		String itemNameAfterEdition = itemEditText.getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}
	
	@Test(dependsOnMethods = { "shouldInplaceEditItemTest" })
	public void shouldDeleteItemTest() {
		// given
		int numberOfRowsBefore = browser.findElements(By.id("removeItemButton")).size();
		WebElement removeButton = browser.findElement(By.id("removeItemButton"));

		// when
		removeButton.click();
		new Actions(browser).pause(1000).perform();
		WebElement confirmRemovalButton = browser.findElement(By.id("confirmItemRemovalButton"));
		confirmRemovalButton.click();
		new Actions(browser).pause(1000).perform();

		// then
		int numberOfRowsAfter = browser.findElements(By.id("removeItemButton")).size();
		assertThat(numberOfRowsAfter).isEqualTo(numberOfRowsBefore - 1);
	}

	@AfterTest
	public void cleanupAndShutdownBrowser() {
		deleteAllItems();
		browser.quit();
	}
	
	private void deleteAllItems() {
		while (browser.findElements(By.id("removeItemButton")).size() > 0) {
			WebElement removeButton = browser.findElement(By.id("removeItemButton"));
			removeButton.click();
			new Actions(browser).pause(1000).perform();
			WebElement confirmRemovalButton = browser.findElement(By.id("confirmItemRemovalButton"));
			confirmRemovalButton.click();
			new Actions(browser).pause(1000).perform();
		}
	}
}
