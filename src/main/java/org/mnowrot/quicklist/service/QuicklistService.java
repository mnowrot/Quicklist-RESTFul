/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mnowrot.quicklist.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mnowrot.quicklist.model.ListItem;

/**
 * Service that makes quicklist operations available
 * 
 * @author PLMANOW4
 * 
 */
@Stateless
public class QuicklistService {

	@PersistenceContext
	private EntityManager em;

	public List<ListItem> getAllItems() {
		return em.createNamedQuery("findAll", ListItem.class).getResultList();
	}

	public ListItem addItem(String listItemName) {
		ListItem newItem = new ListItem();
		newItem.setName(listItemName);
		em.persist(newItem);
		return newItem;
	}

	public void removeItem(Long listItemId) {
		ListItem toRemove = em.find(ListItem.class, listItemId);
		em.remove(toRemove);
	}
}
