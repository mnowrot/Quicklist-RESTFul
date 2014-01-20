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
package org.mnowrot.quicklist.rest;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.mnowrot.quicklist.service.QuicklistService;

@Path("/")
public class QuicklistRestful {
	
	private static final CacheControl NO_CACHE = noCache();
	
	@EJB
	private QuicklistService quicklistService;

	@GET
	@Path("/all")
	@Produces("application/json")
	public Response getAllItems() {
		ResponseBuilder builder = Response.ok(quicklistService.getAllItems());
		builder.cacheControl(NO_CACHE); // workaround for IE9 response caching
		return builder.build();
	}

	@POST
	@Path("/add")
	public void addListItem(@FormParam("newItemName") String newItemName) {
		quicklistService.addItem(newItemName);
	}

	@DELETE
	@Path("/item/{listItemId}")
	public void removeItem(@PathParam("listItemId") Long listItemId) {
		quicklistService.removeItem(listItemId);
	}
	
	private static CacheControl noCache() {
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		cc.setNoStore(true);
		cc.setMustRevalidate(true);
		return cc;
	}
	
	@PUT
	@Path("/item/{listItemId}")
	public void editListItem(@PathParam("listItemId") Long listItemId, 
			@FormParam("editedItemName") String editedItemName) {
		quicklistService.editItemName(listItemId, editedItemName);
	}
}
