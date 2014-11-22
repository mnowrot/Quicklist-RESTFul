/**
 * 
 */
package org.mnowrot.quicklist.smartgwt.client;

import org.mnowrot.quicklist.smartgwt.client.QuicklistView.QuicklistViewListener;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author PLMANOW4
 *
 */
public class QuicklistPresenter implements QuicklistViewListener {
	private QuicklistView view;
	private QuicklistModel model;

	public QuicklistPresenter(QuicklistView view, QuicklistModel model) {
		this.view = view;
		this.model = model;
		
		view.addListener(this);
		RootPanel.get("quicklistAppContainer").add(view.getWrappingWidget());
		view.draw();
	}

}
