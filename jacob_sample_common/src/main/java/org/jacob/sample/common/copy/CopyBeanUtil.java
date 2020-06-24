package org.jacob.sample.common.copy;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.jacob.sample.common.bean1.User1;
import org.jacob.sample.common.bean2.User2;

public class CopyBeanUtil {

	public static void main(String[] args) {
		BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
		User1 user1 = new User1("jacob", "上海浦东", 20);
		User2 user2 = new User2();
		System.err.println("before========" + user2);
		try {
			beanUtilsBean.copyProperties(user2, user1);
			System.err.println("after========" + user2);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
