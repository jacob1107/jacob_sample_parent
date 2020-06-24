package org.jacob.sample.common.string;

import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

public class ConverterUtils {

	public static void main(String[] args) {
		DateConverter dateConverter = new DateConverter();
		String[] datepattern = { "yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss" };
		dateConverter.setPatterns(datepattern);
		ConvertUtils.register(dateConverter, Date.class);
		System.err.println(dateConverter.getLocale());
	}
}
