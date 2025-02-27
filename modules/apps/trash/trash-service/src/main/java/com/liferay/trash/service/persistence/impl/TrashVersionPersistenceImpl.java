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

package com.liferay.trash.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.trash.exception.NoSuchVersionException;
import com.liferay.trash.model.TrashVersion;
import com.liferay.trash.model.impl.TrashVersionImpl;
import com.liferay.trash.model.impl.TrashVersionModelImpl;
import com.liferay.trash.service.persistence.TrashVersionPersistence;
import com.liferay.trash.service.persistence.impl.constants.TrashPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the trash version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = TrashVersionPersistence.class)
public class TrashVersionPersistenceImpl
	extends BasePersistenceImpl<TrashVersion>
	implements TrashVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TrashVersionUtil</code> to access the trash version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TrashVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByEntryId;
	private FinderPath _finderPathWithoutPaginationFindByEntryId;
	private FinderPath _finderPathCountByEntryId;

	/**
	 * Returns all the trash versions where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the matching trash versions
	 */
	@Override
	public List<TrashVersion> findByEntryId(long entryId) {
		return findByEntryId(
			entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the trash versions where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @return the range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByEntryId(long entryId, int start, int end) {
		return findByEntryId(entryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the trash versions where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByEntryId(
		long entryId, int start, int end,
		OrderByComparator<TrashVersion> orderByComparator) {

		return findByEntryId(entryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the trash versions where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByEntryId(
		long entryId, int start, int end,
		OrderByComparator<TrashVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByEntryId;
				finderArgs = new Object[] {entryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByEntryId;
			finderArgs = new Object[] {entryId, start, end, orderByComparator};
		}

		List<TrashVersion> list = null;

		if (useFinderCache) {
			list = (List<TrashVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TrashVersion trashVersion : list) {
					if (entryId != trashVersion.getEntryId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_TRASHVERSION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(TrashVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				list = (List<TrashVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first trash version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching trash version
	 * @throws NoSuchVersionException if a matching trash version could not be found
	 */
	@Override
	public TrashVersion findByEntryId_First(
			long entryId, OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByEntryId_First(
			entryId, orderByComparator);

		if (trashVersion != null) {
			return trashVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append("}");

		throw new NoSuchVersionException(msg.toString());
	}

	/**
	 * Returns the first trash version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByEntryId_First(
		long entryId, OrderByComparator<TrashVersion> orderByComparator) {

		List<TrashVersion> list = findByEntryId(
			entryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last trash version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching trash version
	 * @throws NoSuchVersionException if a matching trash version could not be found
	 */
	@Override
	public TrashVersion findByEntryId_Last(
			long entryId, OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByEntryId_Last(
			entryId, orderByComparator);

		if (trashVersion != null) {
			return trashVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append("}");

		throw new NoSuchVersionException(msg.toString());
	}

	/**
	 * Returns the last trash version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByEntryId_Last(
		long entryId, OrderByComparator<TrashVersion> orderByComparator) {

		int count = countByEntryId(entryId);

		if (count == 0) {
			return null;
		}

		List<TrashVersion> list = findByEntryId(
			entryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the trash versions before and after the current trash version in the ordered set where entryId = &#63;.
	 *
	 * @param versionId the primary key of the current trash version
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next trash version
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion[] findByEntryId_PrevAndNext(
			long versionId, long entryId,
			OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = findByPrimaryKey(versionId);

		Session session = null;

		try {
			session = openSession();

			TrashVersion[] array = new TrashVersionImpl[3];

			array[0] = getByEntryId_PrevAndNext(
				session, trashVersion, entryId, orderByComparator, true);

			array[1] = trashVersion;

			array[2] = getByEntryId_PrevAndNext(
				session, trashVersion, entryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected TrashVersion getByEntryId_PrevAndNext(
		Session session, TrashVersion trashVersion, long entryId,
		OrderByComparator<TrashVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TRASHVERSION_WHERE);

		query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(TrashVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(trashVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<TrashVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the trash versions where entryId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 */
	@Override
	public void removeByEntryId(long entryId) {
		for (TrashVersion trashVersion :
				findByEntryId(
					entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(trashVersion);
		}
	}

	/**
	 * Returns the number of trash versions where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the number of matching trash versions
	 */
	@Override
	public int countByEntryId(long entryId) {
		FinderPath finderPath = _finderPathCountByEntryId;

		Object[] finderArgs = new Object[] {entryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TRASHVERSION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ENTRYID_ENTRYID_2 =
		"trashVersion.entryId = ?";

	private FinderPath _finderPathWithPaginationFindByE_C;
	private FinderPath _finderPathWithoutPaginationFindByE_C;
	private FinderPath _finderPathCountByE_C;

	/**
	 * Returns all the trash versions where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @return the matching trash versions
	 */
	@Override
	public List<TrashVersion> findByE_C(long entryId, long classNameId) {
		return findByE_C(
			entryId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @return the range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByE_C(
		long entryId, long classNameId, int start, int end) {

		return findByE_C(entryId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByE_C(
		long entryId, long classNameId, int start, int end,
		OrderByComparator<TrashVersion> orderByComparator) {

		return findByE_C(
			entryId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByE_C(
		long entryId, long classNameId, int start, int end,
		OrderByComparator<TrashVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByE_C;
				finderArgs = new Object[] {entryId, classNameId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByE_C;
			finderArgs = new Object[] {
				entryId, classNameId, start, end, orderByComparator
			};
		}

		List<TrashVersion> list = null;

		if (useFinderCache) {
			list = (List<TrashVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TrashVersion trashVersion : list) {
					if ((entryId != trashVersion.getEntryId()) ||
						(classNameId != trashVersion.getClassNameId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_TRASHVERSION_WHERE);

			query.append(_FINDER_COLUMN_E_C_ENTRYID_2);

			query.append(_FINDER_COLUMN_E_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(TrashVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				qPos.add(classNameId);

				list = (List<TrashVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching trash version
	 * @throws NoSuchVersionException if a matching trash version could not be found
	 */
	@Override
	public TrashVersion findByE_C_First(
			long entryId, long classNameId,
			OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByE_C_First(
			entryId, classNameId, orderByComparator);

		if (trashVersion != null) {
			return trashVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchVersionException(msg.toString());
	}

	/**
	 * Returns the first trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByE_C_First(
		long entryId, long classNameId,
		OrderByComparator<TrashVersion> orderByComparator) {

		List<TrashVersion> list = findByE_C(
			entryId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching trash version
	 * @throws NoSuchVersionException if a matching trash version could not be found
	 */
	@Override
	public TrashVersion findByE_C_Last(
			long entryId, long classNameId,
			OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByE_C_Last(
			entryId, classNameId, orderByComparator);

		if (trashVersion != null) {
			return trashVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchVersionException(msg.toString());
	}

	/**
	 * Returns the last trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByE_C_Last(
		long entryId, long classNameId,
		OrderByComparator<TrashVersion> orderByComparator) {

		int count = countByE_C(entryId, classNameId);

		if (count == 0) {
			return null;
		}

		List<TrashVersion> list = findByE_C(
			entryId, classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the trash versions before and after the current trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param versionId the primary key of the current trash version
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next trash version
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion[] findByE_C_PrevAndNext(
			long versionId, long entryId, long classNameId,
			OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = findByPrimaryKey(versionId);

		Session session = null;

		try {
			session = openSession();

			TrashVersion[] array = new TrashVersionImpl[3];

			array[0] = getByE_C_PrevAndNext(
				session, trashVersion, entryId, classNameId, orderByComparator,
				true);

			array[1] = trashVersion;

			array[2] = getByE_C_PrevAndNext(
				session, trashVersion, entryId, classNameId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected TrashVersion getByE_C_PrevAndNext(
		Session session, TrashVersion trashVersion, long entryId,
		long classNameId, OrderByComparator<TrashVersion> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_TRASHVERSION_WHERE);

		query.append(_FINDER_COLUMN_E_C_ENTRYID_2);

		query.append(_FINDER_COLUMN_E_C_CLASSNAMEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(TrashVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(trashVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<TrashVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the trash versions where entryId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByE_C(long entryId, long classNameId) {
		for (TrashVersion trashVersion :
				findByE_C(
					entryId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(trashVersion);
		}
	}

	/**
	 * Returns the number of trash versions where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @return the number of matching trash versions
	 */
	@Override
	public int countByE_C(long entryId, long classNameId) {
		FinderPath finderPath = _finderPathCountByE_C;

		Object[] finderArgs = new Object[] {entryId, classNameId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_TRASHVERSION_WHERE);

			query.append(_FINDER_COLUMN_E_C_ENTRYID_2);

			query.append(_FINDER_COLUMN_E_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_E_C_ENTRYID_2 =
		"trashVersion.entryId = ? AND ";

	private static final String _FINDER_COLUMN_E_C_CLASSNAMEID_2 =
		"trashVersion.classNameId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the trash version where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchVersionException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching trash version
	 * @throws NoSuchVersionException if a matching trash version could not be found
	 */
	@Override
	public TrashVersion findByC_C(long classNameId, long classPK)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByC_C(classNameId, classPK);

		if (trashVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchVersionException(msg.toString());
		}

		return trashVersion;
	}

	/**
	 * Returns the trash version where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the trash version where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof TrashVersion) {
			TrashVersion trashVersion = (TrashVersion)result;

			if ((classNameId != trashVersion.getClassNameId()) ||
				(classPK != trashVersion.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_TRASHVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<TrashVersion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					TrashVersion trashVersion = list.get(0);

					result = trashVersion;

					cacheResult(trashVersion);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_C, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (TrashVersion)result;
		}
	}

	/**
	 * Removes the trash version where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the trash version that was removed
	 */
	@Override
	public TrashVersion removeByC_C(long classNameId, long classPK)
		throws NoSuchVersionException {

		TrashVersion trashVersion = findByC_C(classNameId, classPK);

		return remove(trashVersion);
	}

	/**
	 * Returns the number of trash versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching trash versions
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_TRASHVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"trashVersion.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"trashVersion.classPK = ?";

	public TrashVersionPersistenceImpl() {
		setModelClass(TrashVersion.class);

		setModelImplClass(TrashVersionImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the trash version in the entity cache if it is enabled.
	 *
	 * @param trashVersion the trash version
	 */
	@Override
	public void cacheResult(TrashVersion trashVersion) {
		entityCache.putResult(
			entityCacheEnabled, TrashVersionImpl.class,
			trashVersion.getPrimaryKey(), trashVersion);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				trashVersion.getClassNameId(), trashVersion.getClassPK()
			},
			trashVersion);

		trashVersion.resetOriginalValues();
	}

	/**
	 * Caches the trash versions in the entity cache if it is enabled.
	 *
	 * @param trashVersions the trash versions
	 */
	@Override
	public void cacheResult(List<TrashVersion> trashVersions) {
		for (TrashVersion trashVersion : trashVersions) {
			if (entityCache.getResult(
					entityCacheEnabled, TrashVersionImpl.class,
					trashVersion.getPrimaryKey()) == null) {

				cacheResult(trashVersion);
			}
			else {
				trashVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all trash versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TrashVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the trash version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TrashVersion trashVersion) {
		entityCache.removeResult(
			entityCacheEnabled, TrashVersionImpl.class,
			trashVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((TrashVersionModelImpl)trashVersion, true);
	}

	@Override
	public void clearCache(List<TrashVersion> trashVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TrashVersion trashVersion : trashVersions) {
			entityCache.removeResult(
				entityCacheEnabled, TrashVersionImpl.class,
				trashVersion.getPrimaryKey());

			clearUniqueFindersCache((TrashVersionModelImpl)trashVersion, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, TrashVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TrashVersionModelImpl trashVersionModelImpl) {

		Object[] args = new Object[] {
			trashVersionModelImpl.getClassNameId(),
			trashVersionModelImpl.getClassPK()
		};

		finderCache.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C, args, trashVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TrashVersionModelImpl trashVersionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				trashVersionModelImpl.getClassNameId(),
				trashVersionModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}

		if ((trashVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				trashVersionModelImpl.getOriginalClassNameId(),
				trashVersionModelImpl.getOriginalClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}
	}

	/**
	 * Creates a new trash version with the primary key. Does not add the trash version to the database.
	 *
	 * @param versionId the primary key for the new trash version
	 * @return the new trash version
	 */
	@Override
	public TrashVersion create(long versionId) {
		TrashVersion trashVersion = new TrashVersionImpl();

		trashVersion.setNew(true);
		trashVersion.setPrimaryKey(versionId);

		trashVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return trashVersion;
	}

	/**
	 * Removes the trash version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionId the primary key of the trash version
	 * @return the trash version that was removed
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion remove(long versionId) throws NoSuchVersionException {
		return remove((Serializable)versionId);
	}

	/**
	 * Removes the trash version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the trash version
	 * @return the trash version that was removed
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion remove(Serializable primaryKey)
		throws NoSuchVersionException {

		Session session = null;

		try {
			session = openSession();

			TrashVersion trashVersion = (TrashVersion)session.get(
				TrashVersionImpl.class, primaryKey);

			if (trashVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(trashVersion);
		}
		catch (NoSuchVersionException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected TrashVersion removeImpl(TrashVersion trashVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(trashVersion)) {
				trashVersion = (TrashVersion)session.get(
					TrashVersionImpl.class, trashVersion.getPrimaryKeyObj());
			}

			if (trashVersion != null) {
				session.delete(trashVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (trashVersion != null) {
			clearCache(trashVersion);
		}

		return trashVersion;
	}

	@Override
	public TrashVersion updateImpl(TrashVersion trashVersion) {
		boolean isNew = trashVersion.isNew();

		if (!(trashVersion instanceof TrashVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(trashVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					trashVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in trashVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TrashVersion implementation " +
					trashVersion.getClass());
		}

		TrashVersionModelImpl trashVersionModelImpl =
			(TrashVersionModelImpl)trashVersion;

		Session session = null;

		try {
			session = openSession();

			if (trashVersion.isNew()) {
				session.save(trashVersion);

				trashVersion.setNew(false);
			}
			else {
				trashVersion = (TrashVersion)session.merge(trashVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {trashVersionModelImpl.getEntryId()};

			finderCache.removeResult(_finderPathCountByEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByEntryId, args);

			args = new Object[] {
				trashVersionModelImpl.getEntryId(),
				trashVersionModelImpl.getClassNameId()
			};

			finderCache.removeResult(_finderPathCountByE_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByE_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((trashVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					trashVersionModelImpl.getOriginalEntryId()
				};

				finderCache.removeResult(_finderPathCountByEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByEntryId, args);

				args = new Object[] {trashVersionModelImpl.getEntryId()};

				finderCache.removeResult(_finderPathCountByEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByEntryId, args);
			}

			if ((trashVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByE_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					trashVersionModelImpl.getOriginalEntryId(),
					trashVersionModelImpl.getOriginalClassNameId()
				};

				finderCache.removeResult(_finderPathCountByE_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByE_C, args);

				args = new Object[] {
					trashVersionModelImpl.getEntryId(),
					trashVersionModelImpl.getClassNameId()
				};

				finderCache.removeResult(_finderPathCountByE_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByE_C, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, TrashVersionImpl.class,
			trashVersion.getPrimaryKey(), trashVersion, false);

		clearUniqueFindersCache(trashVersionModelImpl, false);
		cacheUniqueFindersCache(trashVersionModelImpl);

		trashVersion.resetOriginalValues();

		return trashVersion;
	}

	/**
	 * Returns the trash version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the trash version
	 * @return the trash version
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByPrimaryKey(primaryKey);

		if (trashVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return trashVersion;
	}

	/**
	 * Returns the trash version with the primary key or throws a <code>NoSuchVersionException</code> if it could not be found.
	 *
	 * @param versionId the primary key of the trash version
	 * @return the trash version
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion findByPrimaryKey(long versionId)
		throws NoSuchVersionException {

		return findByPrimaryKey((Serializable)versionId);
	}

	/**
	 * Returns the trash version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param versionId the primary key of the trash version
	 * @return the trash version, or <code>null</code> if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion fetchByPrimaryKey(long versionId) {
		return fetchByPrimaryKey((Serializable)versionId);
	}

	/**
	 * Returns all the trash versions.
	 *
	 * @return the trash versions
	 */
	@Override
	public List<TrashVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the trash versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @return the range of trash versions
	 */
	@Override
	public List<TrashVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the trash versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of trash versions
	 */
	@Override
	public List<TrashVersion> findAll(
		int start, int end, OrderByComparator<TrashVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the trash versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of trash versions
	 */
	@Override
	public List<TrashVersion> findAll(
		int start, int end, OrderByComparator<TrashVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<TrashVersion> list = null;

		if (useFinderCache) {
			list = (List<TrashVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_TRASHVERSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_TRASHVERSION;

				sql = sql.concat(TrashVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<TrashVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the trash versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TrashVersion trashVersion : findAll()) {
			remove(trashVersion);
		}
	}

	/**
	 * Returns the number of trash versions.
	 *
	 * @return the number of trash versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_TRASHVERSION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "versionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_TRASHVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TrashVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the trash version persistence.
	 */
	@Activate
	public void activate() {
		TrashVersionModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		TrashVersionModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TrashVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TrashVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TrashVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TrashVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEntryId",
			new String[] {Long.class.getName()},
			TrashVersionModelImpl.ENTRYID_COLUMN_BITMASK);

		_finderPathCountByEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEntryId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByE_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TrashVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByE_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TrashVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			TrashVersionModelImpl.ENTRYID_COLUMN_BITMASK |
			TrashVersionModelImpl.CLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByE_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TrashVersionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			TrashVersionModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			TrashVersionModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(TrashVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = TrashPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.trash.model.TrashVersion"),
			true);
	}

	@Override
	@Reference(
		target = TrashPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = TrashPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_TRASHVERSION =
		"SELECT trashVersion FROM TrashVersion trashVersion";

	private static final String _SQL_SELECT_TRASHVERSION_WHERE =
		"SELECT trashVersion FROM TrashVersion trashVersion WHERE ";

	private static final String _SQL_COUNT_TRASHVERSION =
		"SELECT COUNT(trashVersion) FROM TrashVersion trashVersion";

	private static final String _SQL_COUNT_TRASHVERSION_WHERE =
		"SELECT COUNT(trashVersion) FROM TrashVersion trashVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "trashVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TrashVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TrashVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TrashVersionPersistenceImpl.class);

	static {
		try {
			Class.forName(TrashPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}