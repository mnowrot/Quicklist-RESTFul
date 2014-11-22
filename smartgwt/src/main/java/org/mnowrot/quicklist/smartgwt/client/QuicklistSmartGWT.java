package org.mnowrot.quicklist.smartgwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class QuicklistSmartGWT implements EntryPoint {
	@Override
	public void onModuleLoad() {
		QuicklistView view = new QuicklistViewImpl();
		QuicklistModel model = new QuicklistModel();
		
		new QuicklistPresenter(view, model);
		RootPanel.get("quicklistAppContainer").add(view.getWrappingWidget());
	}
}