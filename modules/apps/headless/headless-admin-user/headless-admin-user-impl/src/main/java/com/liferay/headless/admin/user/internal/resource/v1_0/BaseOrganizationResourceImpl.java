/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseOrganizationResourceImpl
	implements OrganizationResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(
		description = "Retrieves the organizations. Results can be paginated, filtered, searched, and sorted."
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/organizations")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Page<Organization> getOrganizationsPage(
			@Parameter(hidden = true) @QueryParam("flatten") Boolean flatten,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations' -d $'{"comment": ___, "customFields": ___, "location": ___, "name": ___, "organizationContactInformation": ___, "parentOrganization": ___, "services": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new organization")
	@POST
	@Path("/organizations")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Organization postOrganization(Organization organization)
		throws Exception {

		return new Organization();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@DELETE
	@Operation(description = "Deletes an organization.")
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "organizationId")}
	)
	@Path("/organizations/{organizationId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public void deleteOrganization(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(description = "Retrieves the organization.")
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "organizationId")}
	)
	@Path("/organizations/{organizationId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Organization getOrganization(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId)
		throws Exception {

		return new Organization();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}' -d $'{"comment": ___, "customFields": ___, "location": ___, "name": ___, "organizationContactInformation": ___, "parentOrganization": ___, "services": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates the organization with the information sent in the request body. Fields not present in the request body are left unchanged."
	)
	@PATCH
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "organizationId")}
	)
	@Path("/organizations/{organizationId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Organization patchOrganization(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId,
			Organization organization)
		throws Exception {

		Organization existingOrganization = getOrganization(organizationId);

		if (organization.getComment() != null) {
			existingOrganization.setComment(organization.getComment());
		}

		if (organization.getDateCreated() != null) {
			existingOrganization.setDateCreated(organization.getDateCreated());
		}

		if (organization.getDateModified() != null) {
			existingOrganization.setDateModified(
				organization.getDateModified());
		}

		if (organization.getImage() != null) {
			existingOrganization.setImage(organization.getImage());
		}

		if (organization.getKeywords() != null) {
			existingOrganization.setKeywords(organization.getKeywords());
		}

		if (organization.getName() != null) {
			existingOrganization.setName(organization.getName());
		}

		if (organization.getNumberOfOrganizations() != null) {
			existingOrganization.setNumberOfOrganizations(
				organization.getNumberOfOrganizations());
		}

		if (organization.getParentOrganization() != null) {
			existingOrganization.setParentOrganization(
				organization.getParentOrganization());
		}

		preparePatch(organization, existingOrganization);

		return putOrganization(organizationId, existingOrganization);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}' -d $'{"comment": ___, "customFields": ___, "location": ___, "name": ___, "organizationContactInformation": ___, "parentOrganization": ___, "services": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Override
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the organization with information sent in the request body. Any missing fields are deleted unless they are required."
	)
	@PUT
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "organizationId")}
	)
	@Path("/organizations/{organizationId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Organization putOrganization(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId,
			Organization organization)
		throws Exception {

		return new Organization();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{parentOrganizationId}/organizations'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(
		description = "Retrieves the parent organization's child organizations. Results can be paginated, filtered, searched, and sorted."
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "parentOrganizationId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/organizations/{parentOrganizationId}/organizations")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Page<Organization> getOrganizationOrganizationsPage(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentOrganizationId") String parentOrganizationId,
			@Parameter(hidden = true) @QueryParam("flatten") Boolean flatten,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	protected Map<String, String> addAction(
		String actionName, GroupedModel groupedModel, String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, String permissionName,
		Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, permissionName,
			contextScopeChecker, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, permissionName, siteId);
	}

	protected void preparePatch(
		Organization organization, Organization existingOrganization) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected GroupLocalService groupLocalService;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;

}