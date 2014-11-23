/**
 * 
 */
package org.mnowrot.quicklist.smartgwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
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
		
		ListGridField id = new ListGridField("id", "ID#");
		ListGridField name = new ListGridField("name", "Name");
		ListGridField options = new ListGridField("options", "Options");
		
		ListGrid listGrid = new ListGrid();
		listGrid.setCanRemoveRecords(true);		  
		listGrid.setWidth(550);  
		listGrid.setHeight(224);  
		listGrid.setShowAllRecords(true);
		listGrid.setEmptyMessage(messages.listEmpty());
		listGrid.setFields(id, name, options);
		
		VLayout mainView = new VLayout(10);  
        mainView.setHeight100();  
        mainView.setWidth100();  
        mainView.addMember(listGrid);
        
        setWrappingWidget(mainView);
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
