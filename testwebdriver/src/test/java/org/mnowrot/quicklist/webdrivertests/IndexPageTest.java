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
import org.openqa.selenium.StaleElementReferenceException;
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
		browser = BrowserProvider.provideBrowserByName(browserName);
		finder = IndexPageElementFinderProvider.provideIndexPageElementFinderByName(pageElementFinderName, browser);
		wait = new FluentWait<WebDriver>(browser).withTimeout(30, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class);
		browser.get(url);
		deleteAllItems();
	}

	@Test
	public void shouldShowOneRowInEmptyTableTest() {
		// given

		// when
		final int tableSize = finder.getItemTableRows().size();

		// then
		assertThat(tableSize).isEqualTo(1);
	}

	@Test(dependsOnMethods = { "shouldShowOneRowInEmptyTableTest" })
	public void shouldAddListItemOnClickTest() {
		// given
		final String itemName = "FirstListItem";
		final WebElement newListItemInput = finder.getNewListItemInput();
		final WebElement addNewListItemButton = finder.getAddNewListItemButton();

		// when
		newListItemInput.sendKeys(itemName);
		addNewListItemButton.click();
		wait.until(driver -> {
			final List<WebElement> newListItemCells = finder.getItemTableFirstRowColumns();
			return newListItemCells != null && newListItemCells.size() == 4;
		});

		// then
		final List<WebElement> newListItemCells = finder.getItemTableFirstRowColumns();
		assertThat(newListItemCells).isNotNull();
		assertThat(newListItemCells).hasSize(4);
		assertThat(newListItemCells.get(1).getText()).isEqualTo(itemName);
	}

	@Test(dependsOnMethods = { "shouldAddListItemOnClickTest" })
	public void shouldAddListItemOnEnterTest() {
		// given
		final String itemName = "SecondListItem";
		final WebElement newListItemInput = finder.getNewListItemInput();

		// when
		newListItemInput.sendKeys(itemName);
		newListItemInput.sendKeys("\n");
		// wait until the browser updates itself after pressing enter
		wait.until(driver -> {
			final List<WebElement> newListItemCells = finder.getItemTableSecondRowColumns();
			return newListItemCells != null && newListItemCells.size() == 4;
		});

		// then
		final List<WebElement> newListItemCells = finder.getItemTableSecondRowColumns();
		assertThat(newListItemCells).isNotNull();
		assertThat(newListItemCells).hasSize(4);
		assertThat(newListItemCells.get(1).getText()).isEqualTo(itemName);
	}

	@Test(dependsOnMethods = { "shouldAddListItemOnEnterTest" })
	public void shouldCancelItemEditionTest() {
		// given
		final WebElement editItemButton = finder.getFirstEditItemButton();
		final WebElement cancelItemEditionButton = finder.getFirstCancelItemEditionButton();
		final String itemNameBeforeEdition = finder.getFirstEditedItem().getText();
		final WebElement itemEditInput = finder.getFirstItemEditInput();

		// when
		editItemButton.click();
		wait.until(driver -> itemEditInput.isDisplayed());
		itemEditInput.sendKeys("djjdkksksjsjksjsk");
		cancelItemEditionButton.click();

		// then
		final String itemNameAfterEdition = finder.getFirstEditedItem().getText();
		assertThat(itemNameAfterEdition).isEqualTo(itemNameBeforeEdition);
	}

	@Test(dependsOnMethods = { "shouldCancelItemEditionTest" })
	public void shouldEditItemOnClickTest() {
		// given
		final WebElement editItemButton = finder.getFirstEditItemButton();
		final WebElement saveEditedItemButton = finder.getFirstSaveEditedItemButton();
		final String editedItemText = "Edited FirstListItem";
		final WebElement itemEditBox = finder.getFirstItemEditInput();

		// when
		editItemButton.click();
		wait.until(driver -> itemEditBox.isDisplayed());
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		saveEditedItemButton.click();
		// wait until the browser updates itself after click and StaleElementReferenceException is no longer thrown on
		// accessing the element to check
		wait.until(driver -> {
			finder.getFirstEditedItem().getText();
			return true;
		});

		// then
		final String itemNameAfterEdition = finder.getFirstEditedItem().getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}

	@Test(dependsOnMethods = { "shouldEditItemOnClickTest" })
	public void shouldEditItemOnEnterTest() {
		// given
		final WebElement editItemButton = finder.getSecondEditItemButton();
		final String editedItemText = "Edited SecondListItem";
		final WebElement itemEditBox = finder.getSecondItemEditInput();

		// when
		editItemButton.click();
		wait.until(driver -> itemEditBox.isDisplayed());
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		itemEditBox.sendKeys("\n");
		// wait until the browser updates itself after pressing enter and StaleElementReferenceException is no longer
		// thrown on accessing the element to check
		wait.until(driver -> {
			finder.getSecondEditedItem().getText();
			return true;
		});

		// then
		final String itemNameAfterEdition = finder.getSecondEditedItem().getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}

	/**
	 * This test fails in IE11 - Actions.doubleClick does not work there
	 * Update the IEDriver when available and re-test.
	 */
	@Test(dependsOnMethods = { "shouldEditItemOnEnterTest" })
	public void shouldInplaceEditItemTest() {
		// given
		final String editedItemText = "Inplace edited FirstListItem";
		WebElement itemEditText = finder.getFirstEditedItem();
		final WebElement itemEditBox = finder.getFirstItemEditInput();

		// when
		new Actions(browser).doubleClick(itemEditText).perform();
		wait.until(driver -> itemEditBox.isDisplayed());
		itemEditBox.clear();
		itemEditBox.sendKeys(editedItemText);
		itemEditBox.sendKeys("\n");
		// wait until the browser updates itself after pressing enter and StaleElementReferenceException is no longer
		// thrown on accessing the element to check
		// also on IE sometimes the edited message arrives partially - wait until it's complete
		wait.until(driver -> {
			final String text = finder.getFirstEditedItem().getText();
			return editedItemText.equals(text);
		});

		// then
		itemEditText = finder.getFirstEditedItem();
		final String itemNameAfterEdition = itemEditText.getText();
		assertThat(itemNameAfterEdition).isEqualTo(editedItemText);
	}

	@Test(dependsOnMethods = { "shouldInplaceEditItemTest" })
	public void shouldDeleteItemTest() {
		// given
		final int numberOfRowsBefore = finder.getItemTableRows().size();
		final WebElement removeButton = finder.getFirstRemoveItemButton();

		// when
		removeButton.click();
		final WebElement confirmRemovalButton = finder.getConfirmItemRemovalButton();
		wait.until(driver -> confirmRemovalButton.isDisplayed());
		confirmRemovalButton.click();
		wait.until(driver -> !confirmRemovalButton.isDisplayed());

		// then
		final List<WebElement> itemTableRows = finder.getItemTableRows();
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
		while (finder.getRemoveItemButtons().size() > 0) {
			// need to resort to a deprecated method to work around a weird timing bug that happens only in Firefox
			// deleting an item freezes without this one second pause
			new Actions(browser).pause(1000).perform();
			final WebElement removeButton = finder.getFirstRemoveItemButton();
			removeButton.click();

			final WebElement confirmRemovalButton = finder.getConfirmItemRemovalButton();
			wait.until(driver -> confirmRemovalButton.isDisplayed());
			confirmRemovalButton.click();
			wait.until(driver -> !confirmRemovalButton.isDisplayed());
		}
	}
}
