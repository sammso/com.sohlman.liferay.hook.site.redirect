package com.sohlman.liferay.hook.site.redirect;

import com.liferay.portal.kernel.model.Group;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class GroupUtilTest {
	
	@Test
	public void test() {
		Group group = Mockito.mock(Group.class);
		
		List<Group> groups = new ArrayList<>();
		groups.add(createGroup("/38254/38283/38418/"));
		groups.add(createGroup("/39013/"));
		groups.add(createGroup("/38254/38283/38418/38218"));
		groups.add(createGroup("/38254/38283/"));
		
		Assert.assertEquals(GroupUtil.findRootestGroup(groups), groups.get(1));
		
	}
	
	private Group createGroup(String treePath) {
		Group group = Mockito.mock(Group.class);
		Mockito.when(group.getTreePath()).thenReturn(treePath);
		return group;
	}
}
