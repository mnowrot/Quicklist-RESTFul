/**
 *
 */
package org.mnowrot.quicklist.webdrivertests.finders;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author PLMANOW4
 *
 */
public class IndexPageElementFinderDefault implements IndexPageElementFinder {

	private final WebDriver browser;

	public IndexPageElementFinderDefault(WebDriver browser) {
		this.browser = browser;
	}

	@Override
	public List<WebElement> getItemTableRows() {
		return browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr"));
	}

	@Override
	public WebElement getNewListItemInput() {
		return browser.findElement(By.id("newListItemInput"));
	}

	@Override
	public WebElement getAddNewListItemButton() {
		return browser.findElement(By.id("addNewListItemButton"));
	}

	@Override
	public List<WebElement> getItemTableFirstRowColumns() {
		return browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr[1]/td"));
	}

	@Override
	public List<WebElement> getItemTableSecondRowColumns() {
		return browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr[2]/td"));
	}

	@Override
	public WebElement getFirstEditItemButton() {
		return browser.findElements(By.id("editItemButton")).get(0);
	}

	@Override
	public WebElement getFirstCancelItemEditionButton() {
		return browser.findElements(By.id("cancelItemEditionButton")).get(0);
	}

	@Override
	public WebElement getFirstEditedItem() {
		return browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[2]"));
	}

	@Override
	public WebElement getFirstItemEditInput() {
		return browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[3]/input"));
	}

	@Override
	public WebElement getFirstSaveEditedItemButton() {
		return browser.findElements(By.id("saveEditedItemButton")).get(0);
	}

	@Override
	public WebElement getSecondEditedItem() {
		return browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[2]/td[2]"));
	}

	@Override
	public WebElement getSecondItemEditInput() {
		return browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[2]/td[3]/input"));
	}

	@Override
	public WebElement getSecondEditItemButton() {
		return browser.findElements(By.id("editItemButton")).get(1);
	}

	@Override
	public WebElement getFirstRemoveItemButton() {
		return browser.findElement(By.id("removeItemButton"));
	}

	@Override
	public WebElement getConfirmItemRemovalButton() {
		return browser.findElement(By.id("confirmItemRemovalButton"));
	}

	@Override
	public List<WebElement> getRemoveItemButtons() {
		return browser.findElements(By.id("removeItemButton"));
	}

}
