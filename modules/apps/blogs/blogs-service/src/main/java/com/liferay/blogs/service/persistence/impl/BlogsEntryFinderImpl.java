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

package com.liferay.blogs.service.persistence.impl;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.model.impl.BlogsEntryImpl;
import com.liferay.blogs.service.persistence.BlogsEntryFinder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = BlogsEntryFinder.class)
public class BlogsEntryFinderImpl
	extends BlogsEntryFinderBaseImpl implements BlogsEntryFinder {

	public static final String COUNT_BY_ORGANIZATION_IDS =
		BlogsEntryFinder.class.getName() + ".countByOrganizationIds";

	public static final String FIND_BY_GROUP_IDS =
		BlogsEntryFinder.class.getName() + ".findByGroupIds";

	public static final String FIND_BY_ORGANIZATION_IDS =
		BlogsEntryFinder.class.getName() + ".findByOrganizationIds";

	public static final String FIND_BY_NO_ASSETS =
		BlogsEntryFinder.class.getName() + ".findByNoAssets";

	@Override
	public int countByOrganizationId(
		long organizationId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return countByOrganizationIds(
			ListUtil.fromArray(organizationId), displayDate, queryDefinition);
	}

	@Override
	public int countByOrganizationIds(
		List<Long> organizationIds, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		Timestamp displayDate_TS = CalendarUtil.getTimestamp(displayDate);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_ORGANIZATION_IDS);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				if (queryDefinition.isExcludeStatus()) {
					sql = _customSQL.appendCriteria(
						sql, "AND (BlogsEntry.status != ?)");
				}
				else {
					sql = _customSQL.appendCriteria(
						sql, "AND (BlogsEntry.status = ?)");
				}
			}

			sql = StringUtil.replace(
				sql, "[$ORGANIZATION_ID$]",
				getOrganizationIds(organizationIds));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (Long organizationId : organizationIds) {
				qPos.add(organizationId);
			}

			qPos.add(displayDate_TS);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<BlogsEntry> findByGroupIds(
		long companyId, long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_GROUP_IDS);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				if (queryDefinition.isExcludeStatus()) {
					sql = _customSQL.appendCriteria(
						sql, "AND (BlogsEntry.status != ?)");
				}
				else {
					sql = _customSQL.appendCriteria(
						sql, "AND (BlogsEntry.status = ?)");
				}
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("BlogsEntry", BlogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(groupId);
			qPos.add(groupId);
			qPos.add(displayDate);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<BlogsEntry>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<BlogsEntry> findByOrganizationId(
		long organizationId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return findByOrganizationIds(
			ListUtil.fromArray(organizationId), displayDate, queryDefinition);
	}

	@Override
	public List<BlogsEntry> findByOrganizationIds(
		List<Long> organizationIds, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		Timestamp displayDate_TS = CalendarUtil.getTimestamp(displayDate);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_ORGANIZATION_IDS);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				if (queryDefinition.isExcludeStatus()) {
					sql = _customSQL.appendCriteria(
						sql, "AND (BlogsEntry.status != ?)");
				}
				else {
					sql = _customSQL.appendCriteria(
						sql, "AND (BlogsEntry.status = ?)");
				}
			}

			sql = StringUtil.replace(
				sql, "[$ORGANIZATION_ID$]",
				getOrganizationIds(organizationIds));
			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("BlogsEntry", BlogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			for (Long organizationId : organizationIds) {
				qPos.add(organizationId);
			}

			qPos.add(displayDate_TS);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<BlogsEntry>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getOrganizationIds(List<Long> organizationIds) {
		if (organizationIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(organizationIds.size() * 2 - 1);

		for (int i = 0; i < organizationIds.size(); i++) {
			sb.append("Users_Orgs.organizationId = ? ");

			if ((i + 1) != organizationIds.size()) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private Portal _portal;

}