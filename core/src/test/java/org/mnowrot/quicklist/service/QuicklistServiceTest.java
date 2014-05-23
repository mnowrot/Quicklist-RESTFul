package org.mnowrot.quicklist.service;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.mnowrot.quicklist.model.ListItem;
import org.testng.annotations.Test;

/**
 *
 *
 * @author PLMANOW4
 *
 */
public class QuicklistServiceTest extends Arquillian {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@EJB
	private QuicklistService quicklistService;

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, QuicklistServiceTest.class.getSimpleName() + ".war")
				.addClass(QuicklistService.class)
				.addClass(ListItem.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsLibraries(Maven.resolver().resolve("org.easytesting:fest-assert:1.4").withTransitivity().asFile());
	}

	@Test
	public void listPersistenceTest() throws Exception {
		// given
		utx.begin();
		em.joinTransaction();

		// when
		quicklistService.addItem("TestListItemName1");
		quicklistService.addItem("TestListItemName2");
		final List<ListItem> allItems = quicklistService.getAllItems();

		// then
		assertThat(allItems).isNotNull();
		assertThat(allItems).isNotEmpty();
		assertThat(allItems).hasSize(2);
		utx.rollback();

	}

	@Test
	public void listDeletionTest() throws Exception {
		// given
		utx.begin();
		em.joinTransaction();
		final ListItem toDelete = quicklistService.addItem("TestListItemName1");
		quicklistService.addItem("TestListItemName2");

		// when
		quicklistService.removeItem(toDelete.getId());
		final List<ListItem> allItems = quicklistService.getAllItems();

		// then
		assertThat(allItems).isNotNull();
		assertThat(allItems).isNotEmpty();
		assertThat(allItems).hasSize(1);
		utx.rollback();
	}

	@Test
	public void listEditionTest() throws Exception {
		// given
		utx.begin();
		em.joinTransaction();
		final ListItem toEdit = quicklistService.addItem("TestListItemName1");
		quicklistService.addItem("TestListItemName2");

		// when
		quicklistService.editItemName(toEdit.getId(), "TestListItemName3");
		final List<ListItem> allItems = quicklistService.getAllItems();
		final ListItem edited = quicklistService.getItemById(toEdit.getId());

		// then
		assertThat(allItems).isNotNull();
		assertThat(allItems).isNotEmpty();
		assertThat(allItems).hasSize(2);
		assertThat(edited).isNotNull();
		assertThat(edited.getName()).isEqualTo("TestListItemName3");
		utx.rollback();
	}
}
