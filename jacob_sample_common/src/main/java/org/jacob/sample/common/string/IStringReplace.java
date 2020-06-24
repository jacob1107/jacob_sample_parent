package org.jacob.sample.common.string;

public class IStringReplace {
	public static void main(String[] args) {
		String idNo = "411424199309092110";
		String re = "****";
		StringBuffer buffer = new StringBuffer(idNo);
		System.err.println(buffer.replace(3, 7, re));

	}

}
