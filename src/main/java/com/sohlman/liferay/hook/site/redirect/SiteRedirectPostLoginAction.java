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

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true, property = {"key=login.events.post"},
	service = LifecycleAction.class
)
public class SiteRedirectPostLoginAction extends Action {

	@Override
	public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ActionException {
		
		try {
			long userId = _portal.getUserId(httpServletRequest);
		
			List<Group> groups = _groupLocalService.getUserSitesGroups(
				userId, false);
			
			if (groups.size()==0) {
				return;
			}
			
			String path = 
				GroupUtil.getGroupFriendlyUrl(
						_layoutLocalService, GroupUtil.findRootestGroup(groups));
			
			if ( path != null ) {

				LastPath lastPath = new LastPath(StringPool.BLANK, path);

				HttpSession session = httpServletRequest.getSession();

				session.setAttribute(WebKeys.LAST_PATH, lastPath);
			}
			
		} catch (PortalException e) {
			_log.error(e);
		}
	}


	@Reference
	private LayoutLocalService _layoutLocalService;
	
	@Reference
	private GroupLocalService _groupLocalService;
	
	@Reference
	private Portal _portal;
	
	private static Log _log = LogFactoryUtil
			.getLog(SiteRedirectPostLoginAction.class);
}
