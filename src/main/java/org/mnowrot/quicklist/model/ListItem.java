/**
 * 
 */
package org.mnowrot.quicklist.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author PLMANOW4
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "findAll", query = "FROM ListItem")
})
public class ListItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long listItemId;
	
	private String text;

	public Long getListItemId() {
		return listItemId;
	}

	public void setListItemId(Long listItemId) {
		this.listItemId = listItemId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
