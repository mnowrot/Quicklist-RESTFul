/**
 * 
 */
package org.mnowrot.quicklist.smartgwt.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author PLMANOW4
 *
 */
interface QuicklistView {
	
	interface QuicklistViewListener {
		
	}

	Widget getWrappingWidget();

	void addListener(QuicklistViewListener quicklistPresenter);
}

	
