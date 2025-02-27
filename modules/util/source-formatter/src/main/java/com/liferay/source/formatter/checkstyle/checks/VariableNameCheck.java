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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class VariableNameCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PARAMETER_DEF, TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if (modifiersDetailAST.branchContains(TokenTypes.LITERAL_PROTECTED) ||
			modifiersDetailAST.branchContains(TokenTypes.LITERAL_PUBLIC)) {

			return;
		}

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameDetailAST.getText();

		_checkCaps(detailAST, name);
		_checkIsVariableName(detailAST, name);

		DetailAST typeDetailAST = detailAST.findFirstToken(TokenTypes.TYPE);

		DetailAST firstChildDetailAST = typeDetailAST.getFirstChild();

		if (firstChildDetailAST == null) {
			return;
		}

		if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
			String typeName = firstChildDetailAST.getText();

			_checkExceptionVariableName(detailAST, name, typeName);
			_checkInstanceVariableName(detailAST, name, typeName);
			_checkTypeName(detailAST, name, typeName);
			_checkTypo(detailAST, name, typeName);
		}

		DetailAST parentDetailAST = getParentWithTokenType(
			detailAST, TokenTypes.CLASS_DEF, TokenTypes.CTOR_DEF,
			TokenTypes.METHOD_DEF);

		if (parentDetailAST == null) {
			return;
		}

		List<DetailAST> assignDetailASTList = getAllChildTokens(
			parentDetailAST, true, TokenTypes.ASSIGN);

		for (DetailAST assignDetailAST : assignDetailASTList) {
			firstChildDetailAST = assignDetailAST.getFirstChild();

			if (firstChildDetailAST == null) {
				continue;
			}

			String methodName = StringPool.BLANK;

			if (equals(assignDetailAST.getParent(), detailAST)) {
				if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
					continue;
				}

				firstChildDetailAST = firstChildDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) {
					methodName = getMethodName(firstChildDetailAST);
				}
			}
			else if ((firstChildDetailAST.getType() == TokenTypes.IDENT) &&
					 name.equals(firstChildDetailAST.getText())) {

				DetailAST nextSiblingDetailAST =
					firstChildDetailAST.getNextSibling();

				if (nextSiblingDetailAST.getType() == TokenTypes.METHOD_CALL) {
					methodName = getMethodName(nextSiblingDetailAST);
				}
			}

			if (methodName.matches("get[A-Z].*")) {
				_checkTypo(detailAST, name, methodName.substring(3));
			}
		}
	}

	protected String getExpectedVariableName(String typeName) {
		if (StringUtil.isUpperCase(typeName) || typeName.matches("[A-Z]+s")) {
			return StringUtil.toLowerCase(typeName);
		}

		if (typeName.startsWith("IDf")) {
			return StringUtil.replaceFirst(typeName, "IDf", "idf");
		}

		if (typeName.startsWith("OSGi")) {
			return StringUtil.replaceFirst(typeName, "OSGi", "osgi");
		}

		for (int i = 0; i < typeName.length(); i++) {
			char c = typeName.charAt(i);

			if (!Character.isLowerCase(c)) {
				continue;
			}

			if (i == 0) {
				return typeName;
			}

			if (i == 1) {
				return StringUtil.toLowerCase(typeName.substring(0, 1)) +
					typeName.substring(1);
			}

			return StringUtil.toLowerCase(typeName.substring(0, i - 1)) +
				typeName.substring(i - 1);
		}

		return StringUtil.toLowerCase(typeName);
	}

	protected static final String MSG_RENAME_VARIABLE = "variable.rename";

	private void _checkCaps(DetailAST detailAST, String name) {
		for (String[] array : _ALL_CAPS_STRINGS) {
			String s = array[1];

			int x = -1;

			while (true) {
				x = name.indexOf(s, x + 1);

				if (x == -1) {
					break;
				}

				int y = x + s.length();

				if ((y != name.length()) &&
					!Character.isUpperCase(name.charAt(y))) {

					continue;
				}

				String newName =
					name.substring(0, x) + array[0] + name.substring(y);

				log(detailAST, MSG_RENAME_VARIABLE, name, newName);
			}
		}
	}

	private void _checkExceptionVariableName(
		DetailAST detailAST, String name, String typeName) {

		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST.getType() == TokenTypes.LITERAL_CATCH) ||
			(detailAST.getType() != TokenTypes.PARAMETER_DEF) ||
			!typeName.endsWith("Exception")) {

			return;
		}

		String absolutePath = getAbsolutePath();

		if (absolutePath.endsWith("ExceptionMapper.java")) {
			String expectedName = getExpectedVariableName(typeName);

			if (!name.equals(expectedName)) {
				log(detailAST, MSG_RENAME_VARIABLE, name, expectedName);
			}
		}
	}

	private void _checkInstanceVariableName(
		DetailAST detailAST, String name, String typeName) {

		if (!name.contentEquals("_instance")) {
			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return;
			}

			if (parentDetailAST.getType() != TokenTypes.CLASS_DEF) {
				parentDetailAST = parentDetailAST.getParent();

				continue;
			}

			DetailAST identDetailAST = parentDetailAST.findFirstToken(
				TokenTypes.IDENT);

			if (!typeName.equals(identDetailAST.getText())) {
				return;
			}

			DetailAST grandParentDetailAST = parentDetailAST.getParent();

			if (grandParentDetailAST != null) {
				return;
			}

			String expectedVariableName = _getExpectedVariableName(
				typeName, "_", "");

			List<DetailAST> variableDeclarationDetailASTList =
				getAllChildTokens(
					parentDetailAST, true, TokenTypes.VARIABLE_DEF);

			for (DetailAST variableDeclarationDetailAST :
					variableDeclarationDetailASTList) {

				identDetailAST = variableDeclarationDetailAST.findFirstToken(
					TokenTypes.IDENT);

				if (expectedVariableName.equals(identDetailAST.getText())) {
					return;
				}
			}

			log(detailAST, MSG_RENAME_VARIABLE, name, expectedVariableName);

			return;
		}
	}

	private void _checkIsVariableName(DetailAST detailAST, String name) {
		if (!_isBooleanType(detailAST.findFirstToken(TokenTypes.TYPE))) {
			return;
		}

		Matcher matcher = _isVariableNamePattern.matcher(name);

		if (!matcher.find()) {
			return;
		}

		String group2 = matcher.group(2);

		String newName = null;

		if (group2.equals("is")) {
			newName =
				StringUtil.toLowerCase(matcher.group(3)) + matcher.group(4);

			if (!Validator.isVariableName(newName)) {
				return;
			}

			newName = matcher.group(1) + newName;
		}
		else {
			newName = matcher.group(1) + matcher.group(3) + matcher.group(4);
		}

		if (!_classHasVariableWithName(detailAST, newName)) {
			log(detailAST, MSG_RENAME_VARIABLE, name, newName);
		}
	}

	private void _checkTypeName(
		DetailAST detailAST, String variableName, String typeName) {

		List<String> enforceTypeNames = getAttributeValues(
			_ENFORCE_TYPE_NAMES_KEY);

		if ((typeName.endsWith("Impl") ||
			 enforceTypeNames.contains(typeName)) &&
			!variableName.matches("(?i).*" + typeName + "[0-9]*")) {

			log(
				detailAST, _MSG_INCORRECT_ENDING_VARIABLE, typeName,
				getExpectedVariableName(typeName));
		}
	}

	private void _checkTypo(
		DetailAST detailAST, String variableName, String typeName) {

		if (StringUtil.isUpperCase(variableName) ||
			typeName.contains(StringPool.UNDERLINE)) {

			return;
		}

		List<String> allowedVariableNames = getAttributeValues(
			_ALLOWED_VARIABLE_NAMES_KEY);

		if (allowedVariableNames.contains(variableName)) {
			return;
		}

		String nameTrailingDigits = _getTrailingDigits(variableName);

		String trimmedName = StringUtil.replaceLast(
			variableName, nameTrailingDigits, StringPool.BLANK);

		String leadingUnderline = StringPool.BLANK;

		if (variableName.startsWith(StringPool.UNDERLINE)) {
			leadingUnderline = StringPool.UNDERLINE;

			trimmedName = trimmedName.substring(1);
		}

		String typeNameTrailingDigits = _getTrailingDigits(typeName);

		String trimmedTypeName = StringUtil.replaceLast(
			typeName, typeNameTrailingDigits, StringPool.BLANK);

		String expectedName = getExpectedVariableName(trimmedTypeName);

		if (StringUtil.equals(trimmedName, expectedName)) {
			return;
		}

		if (StringUtil.equalsIgnoreCase(trimmedName, trimmedTypeName)) {
			for (int i = expectedName.length() - 1; i >= 0; i--) {
				char c1 = trimmedName.charAt(i);

				if (c1 == expectedName.charAt(i)) {
					continue;
				}

				if (i < (expectedName.length() - 1)) {
					char c2 = trimmedName.charAt(i + 1);

					if (Character.isUpperCase(c1) &&
						(Character.isDigit(c2) || Character.isUpperCase(c2))) {

						return;
					}
				}
			}

			log(
				detailAST, _MSG_TYPO_VARIABLE, variableName,
				StringBundler.concat(
					leadingUnderline, expectedName, nameTrailingDigits));

			return;
		}

		trimmedName = StringUtil.toLowerCase(trimmedName);
		trimmedTypeName = StringUtil.toLowerCase(trimmedTypeName);

		if ((trimmedName.charAt(0) != trimmedTypeName.charAt(0)) ||
			(trimmedName.charAt(trimmedName.length() - 1) !=
				trimmedTypeName.charAt(trimmedTypeName.length() - 1))) {

			return;
		}

		int min = Math.min(trimmedName.length(), trimmedTypeName.length());
		int diff = Math.abs(trimmedName.length() - trimmedTypeName.length());

		if ((min < 5) || (diff > 1)) {
			return;
		}

		int i = StringUtil.startsWithWeight(trimmedName, trimmedTypeName);

		trimmedName = trimmedName.substring(i);

		if (trimmedName.startsWith(StringPool.UNDERLINE)) {
			return;
		}

		trimmedTypeName = trimmedTypeName.substring(i);

		for (int j = 1;; j++) {
			if ((j > trimmedName.length()) || (j > trimmedTypeName.length())) {
				break;
			}

			if (trimmedName.charAt(trimmedName.length() - j) !=
					trimmedTypeName.charAt(trimmedTypeName.length() - j)) {

				if (!_containSameCharacters(trimmedName, trimmedTypeName)) {
					return;
				}

				break;
			}
		}

		log(
			detailAST, _MSG_TYPO_VARIABLE, variableName,
			_getExpectedVariableName(
				typeName, leadingUnderline, nameTrailingDigits));
	}

	private boolean _classHasVariableWithName(
		DetailAST detailAST, String variableName) {

		DetailAST parentDetailAST = detailAST.getParent();

		List<DetailAST> definitionDetailASTList = new ArrayList<>();

		while (true) {
			if (parentDetailAST == null) {
				break;
			}

			if (parentDetailAST.getType() == TokenTypes.METHOD_DEF) {
				definitionDetailASTList.addAll(
					getAllChildTokens(
						parentDetailAST, true, TokenTypes.PARAMETER_DEF,
						TokenTypes.VARIABLE_DEF));
			}

			if (parentDetailAST.getType() == TokenTypes.CLASS_DEF) {
				DetailAST objBlockDetailAST = parentDetailAST.findFirstToken(
					TokenTypes.OBJBLOCK);

				definitionDetailASTList.addAll(
					getAllChildTokens(
						objBlockDetailAST, false, TokenTypes.VARIABLE_DEF));
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		for (DetailAST definitionDetailAST : definitionDetailASTList) {
			DetailAST definitionNameDetailAST =
				definitionDetailAST.findFirstToken(TokenTypes.IDENT);

			if (variableName.equals(definitionNameDetailAST.getText())) {
				return true;
			}
		}

		return false;
	}

	private boolean _containSameCharacters(String s1, String s2) {
		char[] chars1 = s1.toCharArray();
		char[] chars2 = s2.toCharArray();

		Arrays.sort(chars1);
		Arrays.sort(chars2);

		return Arrays.equals(chars1, chars2);
	}

	private String _getExpectedVariableName(
		String typeName, String leadingUnderline, String trailingDigits) {

		return StringBundler.concat(
			leadingUnderline, getExpectedVariableName(typeName),
			trailingDigits);
	}

	private String _getTrailingDigits(String s) {
		String digits = StringPool.BLANK;

		for (int i = s.length() - 1; i >= 0; i--) {
			if (Character.isDigit(s.charAt(i))) {
				digits = s.charAt(i) + digits;
			}
			else {
				return digits;
			}
		}

		return digits;
	}

	private boolean _isBooleanType(DetailAST typeDetailAST) {
		DetailAST childDetailAST = typeDetailAST.getFirstChild();

		if (childDetailAST == null) {
			return false;
		}

		if (childDetailAST.getType() == TokenTypes.LITERAL_BOOLEAN) {
			return true;
		}

		if (childDetailAST.getType() == TokenTypes.IDENT) {
			String name = childDetailAST.getText();

			if (name.equals("Boolean")) {
				return true;
			}
		}

		return false;
	}

	private static final String[][] _ALL_CAPS_STRINGS = {
		{"DDL", "Ddl"}, {"DDM", "Ddm"}, {"DL", "Dl"}, {"PK", "Pk"}
	};

	private static final String _ALLOWED_VARIABLE_NAMES_KEY =
		"allowedVariableNames";

	private static final String _ENFORCE_TYPE_NAMES_KEY = "enforceTypeNames";

	private static final String _MSG_INCORRECT_ENDING_VARIABLE =
		"variable.incorrect.ending";

	private static final String _MSG_TYPO_VARIABLE = "variable.typo";

	private static final Pattern _isVariableNamePattern = Pattern.compile(
		"(_?)(is|IS_)([A-Z])(.*)");

}