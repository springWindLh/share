package com.share.support.util;

import java.util.Stack;

public class Nums {
    private static char[] BASE_62_CHAR_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static Integer toInt(Object value) {
        return toInt(value, null);
    }

    public static Integer toInt(Object value, Integer defaultValue) {
        if (null == value) {
            return defaultValue;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return defaultValue;
            } else {
                return new Integer((String) value);
            }
        } else {
            throw new IllegalArgumentException("must input number or string");
        }
    }

    public static Long toLong(Object value) {
        return null == value ? null : (value instanceof Number ? ((Number) value).longValue() : new Long(value.toString()));
    }

    public static Long[] toLongArray(String longArray) {
        if (StringUtil.isEmpty(longArray)) {
            return new Long[0];
        }
        String[] strings = longArray.split(",");
        Long[] longs = new Long[strings.length];
        for (int i = 0; i < strings.length; i++) {
            longs[i] = toLong(strings[i]);
        }
        return longs;
    }

    public static Integer[] toIntArray(String intArray) {
        if (StringUtil.isEmpty(intArray)) {
            return new Integer[0];
        }
        String[] strings = intArray.split(",");
        Integer[] ints = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = toInt(strings[i]);
        }
        return ints;
    }

    public static String toBase62(Number number) {
        Long rest = Math.abs(number.longValue());

        Stack<Character> stack = new Stack<Character>();
        while (rest != 0) {
            int index = new Long((rest - (rest / 62) * 62)).intValue();
            stack.add(BASE_62_CHAR_SET[index]);
            rest = rest / 62;
        }

        StringBuilder result = new StringBuilder();
        for (; !stack.isEmpty();) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    /**
     * 将62进制转换成10进制数
     * @param ident62
     */
    public static Integer fromBase62(String ident62) {
        int decimal = 0;
        int base = 62;
        int keisu = 0;
        int cnt = 0;

        byte ident[] = ident62.getBytes();
        for (int i = ident.length - 1; i >= 0; i--) {
            int num = 0;
            if (ident[i] > 48 && ident[i] <= 57) {
                num = ident[i] - 48;
            }
            else if (ident[i] >= 65 && ident[i] <= 90) {
                num = ident[i] - 65 + 10;
            }
            else if (ident[i] >= 97 && ident[i] <= 122) {
                num = ident[i] - 97 + 10 + 26;
            }
            keisu = (int) java.lang.Math.pow((double) base, (double) cnt);
            decimal += num * keisu;
            cnt++;
        }
        return decimal;
    }
}