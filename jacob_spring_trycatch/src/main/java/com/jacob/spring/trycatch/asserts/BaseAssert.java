package com.jacob.spring.trycatch.asserts;

import java.util.List;

public interface BaseAssert {
	// 具体抛出什么类型的异常，由子类重写
	Exception newException(Object... args);

	// 共通判断-对象空指针
	default void assertNotNull(Object obj) throws Exception {
		if (obj == null)
			throw newException(obj);
	}

	// 共通判断-集合不为空
	default void assertNotListEmpty(List<?> list) throws Exception {
		if (list == null || list.isEmpty())
			throw newException(list);
	}
}
