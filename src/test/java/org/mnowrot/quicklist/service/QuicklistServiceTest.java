package org.mnowrot.quicklist.service;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mnowrot.quicklist.model.ListItem;

/**
 * 
 * 
 * @author PLMANOW4
 * 
 */
@RunWith(Arquillian.class)
public class QuicklistServiceTest {
	
	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@EJB
	private QuicklistService quicklistService;


	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, QuicklistServiceTest.class.getSimpleName() + ".war")
				.addClass(QuicklistService.class)
				.addClass(ListItem.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void getAllItemsTest() {
		Assert.fail("Not yet implemented");
	}

}
