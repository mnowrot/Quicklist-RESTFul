/**
 * 
 */
package org.mnowrot.quicklist.webdrivertests;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.mnowrot.quicklist.webdrivertests.config.BrowserProvider;
import org.mnowrot.quicklist.webdrivertests.config.IndexPageElementFinderProvider;
import org.mnowrot.quicklist.webdrivertests.finders.IndexPageElementFinder;
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
	
	private IndexPageElementFinder finder;

	@BeforeTest
	@Parameters({ "url", "browser", "pageElementFinder" })
	public void prepareBrowserAndPage(String url, String browserName, String pageElementFinderName) {
		this.finder = IndexPageElementFinderProvider.provideIndexPageElementFinderByName(pageElementFinderName);
		this.browser = BrowserProvider.provideBrowserByName(browserName);
		browser.get(url);
		deleteAllItems();
	}

	@Test
	public void shouldShowOneRowInEmptyTableTest() {
		// given

		// when
		int tableSize = finder.getItemTableRows(browser).size();

		// then
		assertThat(tableSize).isEqualTo(1);
	}

	@Test(dependsOnMethods = { "shouldShowOneRowInEmptyTableTest" })
	public void shouldAddListItemOnClickTest() {
		// given
		String itemName = "FirstListItem";
		WebElement newListItemInput = finder.getNewListItemInput(browser);
		WebElement addNewListItemButton = finder.getAddNewListItemButton(browser);

		// when
		newListItemInput.sendKeys(itemName);
		addNewListItemButton.click();

		// then
		List<WebElement> newListItemCells = finder.getItemTableFirstRowColumns(browser);
		assertThat(newListItemCells).isNotNull();
		assertThat(newListItemCells).hasSize(4);
		assertThat(newListItemCells.get(1).getText()).isEqualTo(itemName);
	}

	
	@Test(dependsOnMethods = { "shouldAddListItemOnClickTest" })
	public void shouldAddListItemOnEnterTest() {
		// given
		String itemName = "SecondListItem";
		WebElement newListItemInput = finder.getNewListItemInput(browser);

		// when
		newListItemInput.sendKeys(itemName);
		newListItemInput.sendKeys("\n");

		// then
		List<WebElement> newListItemCells = finder.getItemTableSecondRowColumns(browser);
		assertThat(newListItemCells).isNotNull();
		assertThat(newListItemCells).hasSize(4);
		assertThat(newListItemCells.get(1).getText()).isEqualTo(itemName);
	}
	
	@Test(dependsOnMethods = { "shouldAddListItemOnEnterTest" })
	public void shouldCancelItemEditionTest() {
		// given
		WebElement editItemButton = finder.getFirstEditItemButton(browser);
		WebElement cancelItemEditionButton = finder.getFirstCancelItemEditionButton(browser);
		String itemNameBeforeEdition = finder.getFirstEditedItem(browser).getText();
		WebElement itemEditInput = finder.getFirstItemEditInput(browser);

		// when
		editItemButton.click();
		itemEditInput.sendKeys("djjdkksksjsjksjsk");
		cancelItemEditionButton.click();

		// then
		String itemNameAfterEdition = finder.getFirstEditedItem(browser).getText();
		assertThat(itemNameAfterEdition).isEqualTo(itemNameBeforeEdition);
	}
	
	@Test(dependsOnMethods = { "shouldCancelItemEditionTest" })
	public void shouldEditItemOnClickTest() {
		// given
		WebElement editItemButton = finder.getFirstEditItemButton(browser);
		WebElement saveEditedItemButton = finder.getFirstSaveEditedItemButton(browser);
		String editedItemText = "Edited FirstListItem";
		WebElement itemEditBox = finder.getFirstItemEditInput(browser);

		// when
		editItemButton.click();
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		saveEditedItemButton.click();

		// then
		String itemNameAfterEdition = finder.getFirstEditedItem(browser).getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}

	@Test(dependsOnMethods = { "shouldEditItemOnClickTest" })
	public void shouldEditItemOnEnterTest() {
		// given
		WebElement editItemButton = finder.getSecondEditItemButton(browser);
		String editedItemText = "Edited SecondListItem";
		WebElement itemEditBox = finder.getSecondItemEditInput(browser);

		// when
		editItemButton.click();
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		itemEditBox.sendKeys("\n");

		// then
		String itemNameAfterEdition = finder.getSecondEditedItem(browser).getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}
	
	@Test(dependsOnMethods = { "shouldEditItemOnEnterTest" })
	public void shouldInplaceEditItemTest() {
		// given
		String editedItemText = "Inplace edited FirstListItem";
		WebElement itemEditText = finder.getFirstEditedItem(browser);
		WebElement itemEditBox = finder.getFirstItemEditInput(browser);

		// when
		new Actions(browser).doubleClick(itemEditText).perform();
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		itemEditBox.sendKeys("\n");

		// then
		itemEditText = finder.getFirstEditedItem(browser);
		String itemNameAfterEdition = itemEditText.getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}
	
	@Test(dependsOnMethods = { "shouldInplaceEditItemTest" })
	public void shouldDeleteItemTest() {
		// given
		int numberOfRowsBefore = finder.getItemTableRows(browser).size();
		WebElement removeButton = finder.getFirstRemoveItemButton(browser);

		// when
		removeButton.click();
		new Actions(browser).pause(1000).perform();
		WebElement confirmRemovalButton = finder.getConfirmItemRemovalButton(browser);
		confirmRemovalButton.click();
		new Actions(browser).pause(1000).perform();

		// then
		int numberOfRowsAfter = finder.getItemTableRows(browser).size();
		assertThat(numberOfRowsAfter).isEqualTo(numberOfRowsBefore - 1);
	}

	@AfterTest
	public void cleanupAndShutdownBrowser() {
		deleteAllItems();
		browser.quit();
	}
	
	private void deleteAllItems() {
		while (finder.getRemoveItemButtons(browser).size() > 0) {
			WebElement removeButton = finder.getFirstRemoveItemButton(browser);
			removeButton.click();
			new Actions(browser).pause(1000).perform();
			WebElement confirmRemovalButton = finder.getConfirmItemRemovalButton(browser);
			confirmRemovalButton.click();
			new Actions(browser).pause(1000).perform();
		}
	}
}
