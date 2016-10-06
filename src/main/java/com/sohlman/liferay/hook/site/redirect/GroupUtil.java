/*
	Copyright 2016 Sampsa Sohlman
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.sohlman.liferay.hook.site.redirect;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

public class GroupUtil {
	public static Group findRootestGroup(List<Group> groups) {
		// Find 
		
		int level = Integer.MAX_VALUE;
		
		Group selectedGroup = groups.get(0);
		
		for (Group group : groups ) {
			int currentLevel = StringUtil.split(group.getTreePath(), '/').length;
			
			if ( currentLevel < level ) {
				level = currentLevel;
				selectedGroup = group;
			}
		}
		return selectedGroup;
	}
	
	public static String getGroupFriendlyUrl(LayoutLocalService layoutLocalService, Group group) throws PortalException {

		if ( layoutLocalService.getLayoutsCount(group, true) > 0 ) {
			return "/group".concat(group.getFriendlyURL());
		}
		
		if ( layoutLocalService.getLayoutsCount(group, false) > 0 ) {
			return "/web".concat(group.getFriendlyURL());
		}

		return null;
	}
}
