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

import React, {useEffect} from 'react';
import {createPortal} from 'react-dom';

import {useSelector} from '../store/index';
import DisabledArea from './DisabledArea';
import MasterLayout from './MasterLayout';
import PageEditor from './PageEditor';
import Sidebar from './Sidebar';
import Toolbar from './Toolbar';

export default function App() {
	const masterLayoutData = useSelector(state => state.masterLayoutData);
	const languageId = useSelector(state => state.languageId);

	useEffect(() => {
		AUI().use('portal-available-languages', () => {
			const languageDirection = Liferay.Language.direction[languageId];
			const wrapper = document.getElementById('wrapper');

			if (wrapper) {
				wrapper.dir = languageDirection;
				wrapper.lang = languageId;
			}
		});
	}, [languageId]);

	return (
		<>
			<DisabledArea />
			<Toolbar />
			{masterLayoutData.items ? <MasterLayout /> : <PageEditor />}
			{createPortal(<Sidebar />, document.body)}
		</>
	);
}
