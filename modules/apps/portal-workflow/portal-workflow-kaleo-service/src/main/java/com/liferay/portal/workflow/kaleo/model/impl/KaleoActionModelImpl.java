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

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoActionModel;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the KaleoAction service. Represents a row in the &quot;KaleoAction&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>KaleoActionModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoActionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoActionImpl
 * @generated
 */
public class KaleoActionModelImpl
	extends BaseModelImpl<KaleoAction> implements KaleoActionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo action model instance should use the <code>KaleoAction</code> interface instead.
	 */
	public static final String TABLE_NAME = "KaleoAction";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"kaleoActionId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"kaleoClassName", Types.VARCHAR}, {"kaleoClassPK", Types.BIGINT},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoNodeName", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"executionType", Types.VARCHAR},
		{"script", Types.CLOB}, {"scriptLanguage", Types.VARCHAR},
		{"scriptRequiredContexts", Types.VARCHAR}, {"priority", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoActionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("kaleoClassName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("kaleoClassPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoNodeName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("executionType", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("script", Types.CLOB);
		TABLE_COLUMNS_MAP.put("scriptLanguage", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("scriptRequiredContexts", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table KaleoAction (mvccVersion LONG default 0 not null,kaleoActionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionVersionId LONG,kaleoNodeName VARCHAR(200) null,name VARCHAR(200) null,description STRING null,executionType VARCHAR(20) null,script TEXT null,scriptLanguage VARCHAR(75) null,scriptRequiredContexts STRING null,priority INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table KaleoAction";

	public static final String ORDER_BY_JPQL =
		" ORDER BY kaleoAction.priority ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY KaleoAction.priority ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long EXECUTIONTYPE_COLUMN_BITMASK = 2L;

	public static final long KALEOCLASSNAME_COLUMN_BITMASK = 4L;

	public static final long KALEOCLASSPK_COLUMN_BITMASK = 8L;

	public static final long KALEODEFINITIONVERSIONID_COLUMN_BITMASK = 16L;

	public static final long PRIORITY_COLUMN_BITMASK = 32L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public KaleoActionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _kaleoActionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setKaleoActionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoActionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoAction.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoAction.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<KaleoAction, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<KaleoAction, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoAction, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((KaleoAction)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<KaleoAction, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<KaleoAction, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(KaleoAction)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<KaleoAction, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<KaleoAction, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, KaleoAction>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			KaleoAction.class.getClassLoader(), KaleoAction.class,
			ModelWrapper.class);

		try {
			Constructor<KaleoAction> constructor =
				(Constructor<KaleoAction>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<KaleoAction, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<KaleoAction, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<KaleoAction, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<KaleoAction, Object>>();
		Map<String, BiConsumer<KaleoAction, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<KaleoAction, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", KaleoAction::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<KaleoAction, Long>)KaleoAction::setMvccVersion);
		attributeGetterFunctions.put(
			"kaleoActionId", KaleoAction::getKaleoActionId);
		attributeSetterBiConsumers.put(
			"kaleoActionId",
			(BiConsumer<KaleoAction, Long>)KaleoAction::setKaleoActionId);
		attributeGetterFunctions.put("groupId", KaleoAction::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId", (BiConsumer<KaleoAction, Long>)KaleoAction::setGroupId);
		attributeGetterFunctions.put("companyId", KaleoAction::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<KaleoAction, Long>)KaleoAction::setCompanyId);
		attributeGetterFunctions.put("userId", KaleoAction::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<KaleoAction, Long>)KaleoAction::setUserId);
		attributeGetterFunctions.put("userName", KaleoAction::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<KaleoAction, String>)KaleoAction::setUserName);
		attributeGetterFunctions.put("createDate", KaleoAction::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<KaleoAction, Date>)KaleoAction::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", KaleoAction::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<KaleoAction, Date>)KaleoAction::setModifiedDate);
		attributeGetterFunctions.put(
			"kaleoClassName", KaleoAction::getKaleoClassName);
		attributeSetterBiConsumers.put(
			"kaleoClassName",
			(BiConsumer<KaleoAction, String>)KaleoAction::setKaleoClassName);
		attributeGetterFunctions.put(
			"kaleoClassPK", KaleoAction::getKaleoClassPK);
		attributeSetterBiConsumers.put(
			"kaleoClassPK",
			(BiConsumer<KaleoAction, Long>)KaleoAction::setKaleoClassPK);
		attributeGetterFunctions.put(
			"kaleoDefinitionVersionId",
			KaleoAction::getKaleoDefinitionVersionId);
		attributeSetterBiConsumers.put(
			"kaleoDefinitionVersionId",
			(BiConsumer<KaleoAction, Long>)
				KaleoAction::setKaleoDefinitionVersionId);
		attributeGetterFunctions.put(
			"kaleoNodeName", KaleoAction::getKaleoNodeName);
		attributeSetterBiConsumers.put(
			"kaleoNodeName",
			(BiConsumer<KaleoAction, String>)KaleoAction::setKaleoNodeName);
		attributeGetterFunctions.put("name", KaleoAction::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<KaleoAction, String>)KaleoAction::setName);
		attributeGetterFunctions.put(
			"description", KaleoAction::getDescription);
		attributeSetterBiConsumers.put(
			"description",
			(BiConsumer<KaleoAction, String>)KaleoAction::setDescription);
		attributeGetterFunctions.put(
			"executionType", KaleoAction::getExecutionType);
		attributeSetterBiConsumers.put(
			"executionType",
			(BiConsumer<KaleoAction, String>)KaleoAction::setExecutionType);
		attributeGetterFunctions.put("script", KaleoAction::getScript);
		attributeSetterBiConsumers.put(
			"script", (BiConsumer<KaleoAction, String>)KaleoAction::setScript);
		attributeGetterFunctions.put(
			"scriptLanguage", KaleoAction::getScriptLanguage);
		attributeSetterBiConsumers.put(
			"scriptLanguage",
			(BiConsumer<KaleoAction, String>)KaleoAction::setScriptLanguage);
		attributeGetterFunctions.put(
			"scriptRequiredContexts", KaleoAction::getScriptRequiredContexts);
		attributeSetterBiConsumers.put(
			"scriptRequiredContexts",
			(BiConsumer<KaleoAction, String>)
				KaleoAction::setScriptRequiredContexts);
		attributeGetterFunctions.put("priority", KaleoAction::getPriority);
		attributeSetterBiConsumers.put(
			"priority",
			(BiConsumer<KaleoAction, Integer>)KaleoAction::setPriority);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@Override
	public long getKaleoActionId() {
		return _kaleoActionId;
	}

	@Override
	public void setKaleoActionId(long kaleoActionId) {
		_kaleoActionId = kaleoActionId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getKaleoClassName() {
		if (_kaleoClassName == null) {
			return "";
		}
		else {
			return _kaleoClassName;
		}
	}

	@Override
	public void setKaleoClassName(String kaleoClassName) {
		_columnBitmask |= KALEOCLASSNAME_COLUMN_BITMASK;

		if (_originalKaleoClassName == null) {
			_originalKaleoClassName = _kaleoClassName;
		}

		_kaleoClassName = kaleoClassName;
	}

	public String getOriginalKaleoClassName() {
		return GetterUtil.getString(_originalKaleoClassName);
	}

	@Override
	public long getKaleoClassPK() {
		return _kaleoClassPK;
	}

	@Override
	public void setKaleoClassPK(long kaleoClassPK) {
		_columnBitmask |= KALEOCLASSPK_COLUMN_BITMASK;

		if (!_setOriginalKaleoClassPK) {
			_setOriginalKaleoClassPK = true;

			_originalKaleoClassPK = _kaleoClassPK;
		}

		_kaleoClassPK = kaleoClassPK;
	}

	public long getOriginalKaleoClassPK() {
		return _originalKaleoClassPK;
	}

	@Override
	public long getKaleoDefinitionVersionId() {
		return _kaleoDefinitionVersionId;
	}

	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		_columnBitmask |= KALEODEFINITIONVERSIONID_COLUMN_BITMASK;

		if (!_setOriginalKaleoDefinitionVersionId) {
			_setOriginalKaleoDefinitionVersionId = true;

			_originalKaleoDefinitionVersionId = _kaleoDefinitionVersionId;
		}

		_kaleoDefinitionVersionId = kaleoDefinitionVersionId;
	}

	public long getOriginalKaleoDefinitionVersionId() {
		return _originalKaleoDefinitionVersionId;
	}

	@Override
	public String getKaleoNodeName() {
		if (_kaleoNodeName == null) {
			return "";
		}
		else {
			return _kaleoNodeName;
		}
	}

	@Override
	public void setKaleoNodeName(String kaleoNodeName) {
		_kaleoNodeName = kaleoNodeName;
	}

	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public String getDescription() {
		if (_description == null) {
			return "";
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public String getExecutionType() {
		if (_executionType == null) {
			return "";
		}
		else {
			return _executionType;
		}
	}

	@Override
	public void setExecutionType(String executionType) {
		_columnBitmask |= EXECUTIONTYPE_COLUMN_BITMASK;

		if (_originalExecutionType == null) {
			_originalExecutionType = _executionType;
		}

		_executionType = executionType;
	}

	public String getOriginalExecutionType() {
		return GetterUtil.getString(_originalExecutionType);
	}

	@Override
	public String getScript() {
		if (_script == null) {
			return "";
		}
		else {
			return _script;
		}
	}

	@Override
	public void setScript(String script) {
		_script = script;
	}

	@Override
	public String getScriptLanguage() {
		if (_scriptLanguage == null) {
			return "";
		}
		else {
			return _scriptLanguage;
		}
	}

	@Override
	public void setScriptLanguage(String scriptLanguage) {
		_scriptLanguage = scriptLanguage;
	}

	@Override
	public String getScriptRequiredContexts() {
		if (_scriptRequiredContexts == null) {
			return "";
		}
		else {
			return _scriptRequiredContexts;
		}
	}

	@Override
	public void setScriptRequiredContexts(String scriptRequiredContexts) {
		_scriptRequiredContexts = scriptRequiredContexts;
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public void setPriority(int priority) {
		_columnBitmask = -1L;

		_priority = priority;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), KaleoAction.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public KaleoAction toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, KaleoAction>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		KaleoActionImpl kaleoActionImpl = new KaleoActionImpl();

		kaleoActionImpl.setMvccVersion(getMvccVersion());
		kaleoActionImpl.setKaleoActionId(getKaleoActionId());
		kaleoActionImpl.setGroupId(getGroupId());
		kaleoActionImpl.setCompanyId(getCompanyId());
		kaleoActionImpl.setUserId(getUserId());
		kaleoActionImpl.setUserName(getUserName());
		kaleoActionImpl.setCreateDate(getCreateDate());
		kaleoActionImpl.setModifiedDate(getModifiedDate());
		kaleoActionImpl.setKaleoClassName(getKaleoClassName());
		kaleoActionImpl.setKaleoClassPK(getKaleoClassPK());
		kaleoActionImpl.setKaleoDefinitionVersionId(
			getKaleoDefinitionVersionId());
		kaleoActionImpl.setKaleoNodeName(getKaleoNodeName());
		kaleoActionImpl.setName(getName());
		kaleoActionImpl.setDescription(getDescription());
		kaleoActionImpl.setExecutionType(getExecutionType());
		kaleoActionImpl.setScript(getScript());
		kaleoActionImpl.setScriptLanguage(getScriptLanguage());
		kaleoActionImpl.setScriptRequiredContexts(getScriptRequiredContexts());
		kaleoActionImpl.setPriority(getPriority());

		kaleoActionImpl.resetOriginalValues();

		return kaleoActionImpl;
	}

	@Override
	public int compareTo(KaleoAction kaleoAction) {
		int value = 0;

		if (getPriority() < kaleoAction.getPriority()) {
			value = -1;
		}
		else if (getPriority() > kaleoAction.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoAction)) {
			return false;
		}

		KaleoAction kaleoAction = (KaleoAction)obj;

		long primaryKey = kaleoAction.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public void resetOriginalValues() {
		KaleoActionModelImpl kaleoActionModelImpl = this;

		kaleoActionModelImpl._originalCompanyId =
			kaleoActionModelImpl._companyId;

		kaleoActionModelImpl._setOriginalCompanyId = false;

		kaleoActionModelImpl._setModifiedDate = false;

		kaleoActionModelImpl._originalKaleoClassName =
			kaleoActionModelImpl._kaleoClassName;

		kaleoActionModelImpl._originalKaleoClassPK =
			kaleoActionModelImpl._kaleoClassPK;

		kaleoActionModelImpl._setOriginalKaleoClassPK = false;

		kaleoActionModelImpl._originalKaleoDefinitionVersionId =
			kaleoActionModelImpl._kaleoDefinitionVersionId;

		kaleoActionModelImpl._setOriginalKaleoDefinitionVersionId = false;

		kaleoActionModelImpl._originalExecutionType =
			kaleoActionModelImpl._executionType;

		kaleoActionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<KaleoAction> toCacheModel() {
		KaleoActionCacheModel kaleoActionCacheModel =
			new KaleoActionCacheModel();

		kaleoActionCacheModel.mvccVersion = getMvccVersion();

		kaleoActionCacheModel.kaleoActionId = getKaleoActionId();

		kaleoActionCacheModel.groupId = getGroupId();

		kaleoActionCacheModel.companyId = getCompanyId();

		kaleoActionCacheModel.userId = getUserId();

		kaleoActionCacheModel.userName = getUserName();

		String userName = kaleoActionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			kaleoActionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			kaleoActionCacheModel.createDate = createDate.getTime();
		}
		else {
			kaleoActionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			kaleoActionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			kaleoActionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		kaleoActionCacheModel.kaleoClassName = getKaleoClassName();

		String kaleoClassName = kaleoActionCacheModel.kaleoClassName;

		if ((kaleoClassName != null) && (kaleoClassName.length() == 0)) {
			kaleoActionCacheModel.kaleoClassName = null;
		}

		kaleoActionCacheModel.kaleoClassPK = getKaleoClassPK();

		kaleoActionCacheModel.kaleoDefinitionVersionId =
			getKaleoDefinitionVersionId();

		kaleoActionCacheModel.kaleoNodeName = getKaleoNodeName();

		String kaleoNodeName = kaleoActionCacheModel.kaleoNodeName;

		if ((kaleoNodeName != null) && (kaleoNodeName.length() == 0)) {
			kaleoActionCacheModel.kaleoNodeName = null;
		}

		kaleoActionCacheModel.name = getName();

		String name = kaleoActionCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			kaleoActionCacheModel.name = null;
		}

		kaleoActionCacheModel.description = getDescription();

		String description = kaleoActionCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			kaleoActionCacheModel.description = null;
		}

		kaleoActionCacheModel.executionType = getExecutionType();

		String executionType = kaleoActionCacheModel.executionType;

		if ((executionType != null) && (executionType.length() == 0)) {
			kaleoActionCacheModel.executionType = null;
		}

		kaleoActionCacheModel.script = getScript();

		String script = kaleoActionCacheModel.script;

		if ((script != null) && (script.length() == 0)) {
			kaleoActionCacheModel.script = null;
		}

		kaleoActionCacheModel.scriptLanguage = getScriptLanguage();

		String scriptLanguage = kaleoActionCacheModel.scriptLanguage;

		if ((scriptLanguage != null) && (scriptLanguage.length() == 0)) {
			kaleoActionCacheModel.scriptLanguage = null;
		}

		kaleoActionCacheModel.scriptRequiredContexts =
			getScriptRequiredContexts();

		String scriptRequiredContexts =
			kaleoActionCacheModel.scriptRequiredContexts;

		if ((scriptRequiredContexts != null) &&
			(scriptRequiredContexts.length() == 0)) {

			kaleoActionCacheModel.scriptRequiredContexts = null;
		}

		kaleoActionCacheModel.priority = getPriority();

		return kaleoActionCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<KaleoAction, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<KaleoAction, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoAction, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((KaleoAction)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<KaleoAction, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<KaleoAction, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoAction, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((KaleoAction)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, KaleoAction>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _mvccVersion;
	private long _kaleoActionId;
	private long _groupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _kaleoClassName;
	private String _originalKaleoClassName;
	private long _kaleoClassPK;
	private long _originalKaleoClassPK;
	private boolean _setOriginalKaleoClassPK;
	private long _kaleoDefinitionVersionId;
	private long _originalKaleoDefinitionVersionId;
	private boolean _setOriginalKaleoDefinitionVersionId;
	private String _kaleoNodeName;
	private String _name;
	private String _description;
	private String _executionType;
	private String _originalExecutionType;
	private String _script;
	private String _scriptLanguage;
	private String _scriptRequiredContexts;
	private int _priority;
	private long _columnBitmask;
	private KaleoAction _escapedModel;

}