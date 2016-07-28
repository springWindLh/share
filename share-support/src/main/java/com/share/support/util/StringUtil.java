package com.share.support.util;

import java.util.List;

public class StringUtil {
	public static String join(String linker, String prefix, String suffix,
			Object[] values) {
		assert null != values;
		if (values.length == 0) {
			return "";
		}
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < values.length - 1; i = i + 1) {
			stringBuffer.append(prefix).append(values[i]).append(suffix)
					.append(linker);
		}
		stringBuffer.append(prefix).append(values[values.length - 1])
				.append(suffix);
		return stringBuffer.toString();
	}

	public static String join(String linker, Object[] values) {
		return join(linker, "", "", values);
	}

	public static String join(String linker, List<Object> values) {
		return join(linker, values.toArray());
	}

	/**
     * 在数组中搜索包含特定字符串的项
     */
	public static String searchContains(String[] values, String value,
			String defaultValue) {
		for (String _value : values) {
			if (containsIgnoreCase(_value, value)) {
				return _value;
			}
		}
		return defaultValue;
	}

	public static Boolean containsIgnoreCase(String string, String str) {
		return null != string && null != str
				&& string.toUpperCase().contains(str.toUpperCase());
	}
	
	/**
     * 用StringBuffer拼接字符串
     */
    public static String concat(String... items) {
        assert null != items;
        StringBuffer stringBuffer = new StringBuffer();
        for (String each : items) {
            stringBuffer.append(each);
        }
        return stringBuffer.toString();
    }

    public static Boolean isEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }
}
