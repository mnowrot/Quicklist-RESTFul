/**
 * 
 */
package org.mnowrot.quicklist.smartgwt.client;

import org.mnowrot.quicklist.smartgwt.client.QuicklistView.QuicklistViewListener;

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
	}

}
