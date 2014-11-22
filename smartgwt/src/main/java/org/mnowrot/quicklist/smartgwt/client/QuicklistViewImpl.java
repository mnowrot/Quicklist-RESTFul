/**
 * 
 */
package org.mnowrot.quicklist.smartgwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.BaseWidget;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * @author PLMANOW4
 *
 */
public class QuicklistViewImpl implements QuicklistView {
	
	List<QuicklistViewListener> listeners = new ArrayList<>();
	
	private Widget wrappingWidget;

	public QuicklistViewImpl() {
		super();
		
		
		VLayout mainView = new VLayout(10);  
        mainView.setHeight100();  
        mainView.setWidth100();  
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

	@Override
	public void draw() {
		if(wrappingWidget instanceof BaseWidget) {
			((BaseWidget) wrappingWidget).draw();
		}
	}

}
