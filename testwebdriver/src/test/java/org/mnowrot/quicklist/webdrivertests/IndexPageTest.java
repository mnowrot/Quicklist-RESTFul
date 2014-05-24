/**
 *
 */
package org.mnowrot.quicklist.webdrivertests;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.mnowrot.quicklist.webdrivertests.config.BrowserProvider;
import org.mnowrot.quicklist.webdrivertests.config.IndexPageElementFinderProvider;
import org.mnowrot.quicklist.webdrivertests.finders.IndexPageElementFinder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
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

	private Wait<WebDriver> wait;

	@BeforeTest
	@Parameters({ "url", "browser", "pageElementFinder" })
	public void prepareBrowserAndPage(String url, String browserName, String pageElementFinderName) {
		finder = IndexPageElementFinderProvider.provideIndexPageElementFinderByName(pageElementFinderName);
		browser = BrowserProvider.provideBrowserByName(browserName);
		browser.get(url);
		wait = new FluentWait<WebDriver>(browser).withTimeout(30, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS);
		deleteAllItems();
	}

	@Test
	public void shouldShowOneRowInEmptyTableTest() {
		// given

		// when
		final int tableSize = finder.getItemTableRows(browser).size();

		// then
		assertThat(tableSize).isEqualTo(1);
	}

	@Test(dependsOnMethods = { "shouldShowOneRowInEmptyTableTest" })
	public void shouldAddListItemOnClickTest() {
		// given
		final String itemName = "FirstListItem";
		final WebElement newListItemInput = finder.getNewListItemInput(browser);
		final WebElement addNewListItemButton = finder.getAddNewListItemButton(browser);

		// when
		newListItemInput.sendKeys(itemName);
		addNewListItemButton.click();

		// then
		final List<WebElement> newListItemCells = finder.getItemTableFirstRowColumns(browser);
		assertThat(newListItemCells).isNotNull();
		assertThat(newListItemCells).hasSize(4);
		assertThat(newListItemCells.get(1).getText()).isEqualTo(itemName);
	}

	@Test(dependsOnMethods = { "shouldAddListItemOnClickTest" })
	public void shouldAddListItemOnEnterTest() {
		// given
		final String itemName = "SecondListItem";
		final WebElement newListItemInput = finder.getNewListItemInput(browser);

		// when
		newListItemInput.sendKeys(itemName);
		newListItemInput.sendKeys("\n");

		// then
		final List<WebElement> newListItemCells = finder.getItemTableSecondRowColumns(browser);
		assertThat(newListItemCells).isNotNull();
		assertThat(newListItemCells).hasSize(4);
		assertThat(newListItemCells.get(1).getText()).isEqualTo(itemName);
	}

	@Test(dependsOnMethods = { "shouldAddListItemOnEnterTest" })
	public void shouldCancelItemEditionTest() {
		// given
		final WebElement editItemButton = finder.getFirstEditItemButton(browser);
		final WebElement cancelItemEditionButton = finder.getFirstCancelItemEditionButton(browser);
		final String itemNameBeforeEdition = finder.getFirstEditedItem(browser).getText();
		final WebElement itemEditInput = finder.getFirstItemEditInput(browser);

		// when
		editItemButton.click();
		itemEditInput.sendKeys("djjdkksksjsjksjsk");
		cancelItemEditionButton.click();

		// then
		final String itemNameAfterEdition = finder.getFirstEditedItem(browser).getText();
		assertThat(itemNameAfterEdition).isEqualTo(itemNameBeforeEdition);
	}

	@Test(dependsOnMethods = { "shouldCancelItemEditionTest" })
	public void shouldEditItemOnClickTest() {
		// given
		final WebElement editItemButton = finder.getFirstEditItemButton(browser);
		final WebElement saveEditedItemButton = finder.getFirstSaveEditedItemButton(browser);
		final String editedItemText = "Edited FirstListItem";
		final WebElement itemEditBox = finder.getFirstItemEditInput(browser);

		// when
		editItemButton.click();
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		saveEditedItemButton.click();

		// then
		final String itemNameAfterEdition = finder.getFirstEditedItem(browser).getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}

	@Test(dependsOnMethods = { "shouldEditItemOnClickTest" })
	public void shouldEditItemOnEnterTest() {
		// given
		final WebElement editItemButton = finder.getSecondEditItemButton(browser);
		final String editedItemText = "Edited SecondListItem";
		final WebElement itemEditBox = finder.getSecondItemEditInput(browser);

		// when
		editItemButton.click();
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		itemEditBox.sendKeys("\n");

		// then
		final String itemNameAfterEdition = finder.getSecondEditedItem(browser).getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}

	@Test(dependsOnMethods = { "shouldEditItemOnEnterTest" })
	public void shouldInplaceEditItemTest() {
		// given
		final String editedItemText = "Inplace edited FirstListItem";
		WebElement itemEditText = finder.getFirstEditedItem(browser);
		final WebElement itemEditBox = finder.getFirstItemEditInput(browser);

		// when
		new Actions(browser).doubleClick(itemEditText).perform();
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		itemEditBox.sendKeys("\n");

		// then
		itemEditText = finder.getFirstEditedItem(browser);
		final String itemNameAfterEdition = itemEditText.getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}

	@Test(dependsOnMethods = { "shouldInplaceEditItemTest" })
	public void shouldDeleteItemTest() {
		// given
		final int numberOfRowsBefore = finder.getItemTableRows(browser).size();
		final WebElement removeButton = finder.getFirstRemoveItemButton(browser);

		// when
		removeButton.click();
		final WebElement confirmRemovalButton = finder.getConfirmItemRemovalButton(browser);
		wait.until(driver -> confirmRemovalButton.isDisplayed());
		confirmRemovalButton.click();
		wait.until(driver -> !confirmRemovalButton.isDisplayed());

		// then
		final List<WebElement> itemTableRows = finder.getItemTableRows(browser);
		final int numberOfRowsAfter = itemTableRows.size();
		assertThat(numberOfRowsAfter).isEqualTo(numberOfRowsBefore - 1);
	}

	@AfterTest
	public void cleanupAndShutdownBrowser() {
		deleteAllItems();
		browser.quit();
	}

	@SuppressWarnings("deprecation")
	private void deleteAllItems() {
		while (finder.getRemoveItemButtons(browser).size() > 0) {
			// need to resort to a deprecated method to work around a weird timing bug that happens only in Firefox
			// deleting an item freezes without this one second pause
			new Actions(browser).pause(1000).perform();
			final WebElement removeButton = finder.getFirstRemoveItemButton(browser);
			removeButton.click();

			final WebElement confirmRemovalButton = finder.getConfirmItemRemovalButton(browser);
			wait.until(driver -> confirmRemovalButton.isDisplayed());
			confirmRemovalButton.click();
			wait.until(driver -> !confirmRemovalButton.isDisplayed());
		}
	}
}
