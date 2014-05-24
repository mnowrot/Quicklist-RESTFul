package org.mnowrot.quicklist.webdrivertests.finders;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface IndexPageElementFinder {

	public List<WebElement> getItemTableRows(WebDriver browser);

	public WebElement getNewListItemInput(WebDriver browser);

	public WebElement getAddNewListItemButton(WebDriver browser);

	public List<WebElement> getItemTableFirstRowColumns(WebDriver browser);

	public List<WebElement> getItemTableSecondRowColumns(WebDriver browser);

	public WebElement getFirstEditItemButton(WebDriver browser);

	public WebElement getFirstCancelItemEditionButton(WebDriver browser);

	public WebElement getFirstEditedItem(WebDriver browser);

	public WebElement getFirstItemEditInput(WebDriver browser);

	public WebElement getFirstSaveEditedItemButton(WebDriver browser);

	public WebElement getSecondEditedItem(WebDriver browser);

	public WebElement getSecondItemEditInput(WebDriver browser);

	public WebElement getSecondEditItemButton(WebDriver browser);

	public WebElement getFirstRemoveItemButton(WebDriver browser);

	public WebElement getConfirmItemRemovalButton(WebDriver browser);

	public List<WebElement> getRemoveItemButtons(WebDriver browser);

	public WebElement getConfirmItemRemovalDialog(WebDriver browser);

}