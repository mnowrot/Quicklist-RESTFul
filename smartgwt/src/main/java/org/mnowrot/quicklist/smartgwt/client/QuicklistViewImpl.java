/**
 * 
 */
package org.mnowrot.quicklist.smartgwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author PLMANOW4
 *
 */
public class QuicklistViewImpl implements QuicklistView {
	
	List<QuicklistViewListener> listeners = new ArrayList<>();

	@Override
	public Widget getWrappingWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(QuicklistViewListener listener) {
		// TODO Auto-generated method stub
		
	}

}
