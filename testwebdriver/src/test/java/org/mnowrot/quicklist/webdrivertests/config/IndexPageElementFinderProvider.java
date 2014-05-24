/**
 *
 */
package org.mnowrot.quicklist.webdrivertests.config;

import org.mnowrot.quicklist.webdrivertests.finders.IndexPageElementFinder;
import org.mnowrot.quicklist.webdrivertests.finders.IndexPageElementFinderDefault;
import org.openqa.selenium.WebDriver;

/**
 * @author PLMANOW4
 *
 */
public class IndexPageElementFinderProvider {

	public static IndexPageElementFinder provideIndexPageElementFinderByName(String pageElementFinderName,
			WebDriver browser) {
		switch (pageElementFinderName) {
			default:
				return new IndexPageElementFinderDefault(browser);
		}
	}

}
