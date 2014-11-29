/**
 * 
 */
package org.mnowrot.quicklist.smartgwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.HeaderClickEvent;
import com.smartgwt.client.widgets.grid.events.HeaderClickHandler;
import com.smartgwt.client.widgets.grid.events.HeaderDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.HeaderDoubleClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * @author PLMANOW4
 *
 */
public class QuicklistViewImpl implements QuicklistView {
	
	Messages messages = GWT.create(Messages.class);
	
	List<QuicklistViewListener> listeners = new ArrayList<>();
	
	private Widget wrappingWidget;

	public QuicklistViewImpl() {
		super();
        
        VLayout jumbotron = createJumbotron();
		
		ListGrid listGrid = createGrid();
		
		VLayout mainView = new VLayout(10);  
        mainView.setWidth("99%");
        mainView.setLeft(-7);
        mainView.setTop(-7);
        mainView.addMembers(jumbotron, listGrid);
        
        setWrappingWidget(mainView);
 	}

	private final ListGrid createGrid() {
		ListGridField id = new ListGridField("id", messages.id());
		id.setWidth("5%");
		ListGridField name = new ListGridField("name", messages.name());
		name.setWidth("28%");
		ListGridField options = new ListGridField("options", messages.options());
		
		ListGrid listGrid = new ListGrid();
		listGrid.setMargin(7);
		listGrid.setShowSortArrow(SortArrow.NONE);
		listGrid.setShowHeaderContextMenu(false);
		listGrid.setShowHeaderMenuButton(false);
		listGrid.setCanReorderFields(false);
		listGrid.setCanResizeFields(false);
		listGrid.setCanRemoveRecords(true);
		listGrid.addHeaderClickHandler(new HeaderClickHandler() {			
			@Override
			public void onHeaderClick(HeaderClickEvent event) {
				event.cancel();				
			}
		});
		listGrid.addHeaderDoubleClickHandler(new HeaderDoubleClickHandler() {			
			@Override
			public void onHeaderDoubleClick(HeaderDoubleClickEvent event) {
			}
		});
		listGrid.setAutoFitData(Autofit.VERTICAL);
		listGrid.setEmptyMessage(messages.listEmpty());
		listGrid.setFields(id, name, options);
		return listGrid;
	}

	private final VLayout createJumbotron() {
		Label pageMainLabel = new Label(messages.enterNewItem());
        pageMainLabel.setStyleName("mainLabel");
        pageMainLabel.setHeight(80);
        
		DynamicForm form = createJumbotronForm();
        
		VLayout jumbotron = new VLayout(10);  
		jumbotron.setBackgroundColor("#EEEEEE");
		jumbotron.addMembers(pageMainLabel, form);
		return jumbotron;
	}

	private final DynamicForm createJumbotronForm() {
		TextItem textItem = new TextItem();  
        textItem.setTitle(messages.newListItem());
        
        ButtonItem submit = new ButtonItem(messages.submitButton());
        submit.setStartRow(false);
        
		DynamicForm form = new DynamicForm();
		form.setStyleName("paddingBottom10px");
		form.setNumCols(3);
        form.setColWidths(120, 120, "*");
        form.setFields(textItem, submit);
		return form;
	}

	private final void setWrappingWidget(Widget wrappingWidget) {
		this.wrappingWidget = wrappingWidget;
	}

	@Override
	public Widget getWrappingWidget() {
		return wrappingWidget;
	}

	@Override
	public void addListener(QuicklistViewListener listener) {
		listeners.add(listener);
	}
}
