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
	
	@Override
	public List<WebElement> getItemTableRows(WebDriver browser) {
		return browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr"));
	}
	
	@Override
	public WebElement getNewListItemInput(WebDriver browser) {
		return browser.findElement(By.id("newListItemInput"));
	}
	

	@Override
	public WebElement getAddNewListItemButton(WebDriver browser) {
		return browser.findElement(By.id("addNewListItemButton"));
	}
	
	@Override
	public List<WebElement> getItemTableFirstRowColumns(WebDriver browser) {
		return browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr[1]/td"));
	}
	
	@Override
	public List<WebElement> getItemTableSecondRowColumns(WebDriver browser) {
		return browser.findElement(By.id("listItemsTable")).findElements(By.xpath("tbody/tr[2]/td"));
	}

	@Override
	public WebElement getFirstEditItemButton(WebDriver browser) {
		return browser.findElements(By.id("editItemButton")).get(0);
	}
	
	@Override
	public WebElement getFirstCancelItemEditionButton(WebDriver browser) {
		return browser.findElements(By.id("cancelItemEditionButton")).get(0);
	}
	
	@Override
	public WebElement getFirstEditedItem(WebDriver browser) {
		return browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[2]"));
	}
	
	@Override
	public WebElement getFirstItemEditInput(WebDriver browser) {
		return browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[1]/td[3]/input"));
	}
	
	@Override
	public WebElement getFirstSaveEditedItemButton(WebDriver browser) {
		return browser.findElements(By.id("saveEditedItemButton")).get(0);
	}
	
	@Override
	public WebElement getSecondEditedItem(WebDriver browser) {
		return browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[2]/td[2]"));
	}

	@Override
	public WebElement getSecondItemEditInput(WebDriver browser) {
		return browser.findElement(By.id("listItemsTable")).findElement(By.xpath("tbody/tr[2]/td[3]/input"));
	}

	@Override
	public WebElement getSecondEditItemButton(WebDriver browser) {
		return browser.findElements(By.id("editItemButton")).get(1);
	}
	
	@Override
	public WebElement getFirstRemoveItemButton(WebDriver browser) {
		return browser.findElement(By.id("removeItemButton"));
	}
	
	@Override
	public WebElement getConfirmItemRemovalButton(WebDriver browser) {
		return browser.findElement(By.id("confirmItemRemovalButton"));
	}
	
	@Override
	public List<WebElement> getRemoveItemButtons(WebDriver browser) {
		return browser.findElements(By.id("removeItemButton"));
	}


}
