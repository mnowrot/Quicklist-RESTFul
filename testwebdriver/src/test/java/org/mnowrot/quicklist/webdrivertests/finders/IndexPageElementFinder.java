package org.mnowrot.quicklist.webdrivertests.finders;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface IndexPageElementFinder {

	public List<WebElement> getItemTableRows();

	public WebElement getNewListItemInput();

	public WebElement getAddNewListItemButton();

	public List<WebElement> getItemTableFirstRowColumns();

	public List<WebElement> getItemTableSecondRowColumns();

	public WebElement getFirstEditItemButton();

	public WebElement getFirstCancelItemEditionButton();

	public WebElement getFirstEditedItem();

	public WebElement getFirstItemEditInput();

	public WebElement getFirstSaveEditedItemButton();

	public WebElement getSecondEditedItem();

	public WebElement getSecondItemEditInput();

	public WebElement getSecondEditItemButton();

	public WebElement getFirstRemoveItemButton();

	public WebElement getConfirmItemRemovalButton();

	public List<WebElement> getRemoveItemButtons();

}