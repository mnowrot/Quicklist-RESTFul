/**
 * 
 */
package org.mnowrot.quicklist.webdrivertests.config;

import org.mnowrot.quicklist.webdrivertests.finders.IndexPageElementFinder;
import org.mnowrot.quicklist.webdrivertests.finders.IndexPageElementFinderDefault;

/**
 * @author PLMANOW4
 *
 */
public class IndexPageElementFinderProvider {

	public static IndexPageElementFinder provideIndexPageElementFinderByName(
			String pageElementFinderName) {
		switch(pageElementFinderName) {
		default:
			return new IndexPageElementFinderDefault();
		}
	}

}
