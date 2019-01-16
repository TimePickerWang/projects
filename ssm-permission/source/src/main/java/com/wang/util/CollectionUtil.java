package com.wang.util;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Create by wangjf
 * Date 2019/1/13 17:52
 */
public class CollectionUtil {

	// 判断两个list内所含的元素是否相等，是则返回true
	public static <T> boolean isListEqual(List<T> listOne, List<T> listTwo) {
		if (listOne.size() == listTwo.size()) {
			Set<T> listOneSet = Sets.newHashSet(listOne);
			Set<T> listTwoSet = Sets.newHashSet(listTwo);
			listOneSet.removeAll(listTwoSet);
			return CollectionUtils.isEmpty(listOneSet);
		}
		return false;
	}


}
