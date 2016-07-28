package com.share.support.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Jsons {
	/**
	 * 对象转json
	 */
	public static String toJson(Object source) {
		try {
			return JSON.toJSONStringWithDateFormat(source, "yyyy-MM-dd HH:mm:ss",
					SerializerFeature.DisableCircularReferenceDetect);// 用fastjson序列化
		} catch (Throwable e) {
			return "{\"error\":\"" + e + "\"}"; // 返回错误信息
		}
	}

	public static byte[] toJsonByteArray(Object source) {
		return JSON.toJSONBytes(source);
	}

	public static Map toMap(String source) {
		try {
			return JSON.parseObject(source, Map.class);
		} catch (Exception e) {
			throw new RuntimeException("JsonToMapError " + source + " " + e.getMessage(), e);
		}
	}

	public static List<Map> toMapList(String source) {
		try {
			return JSON.parseArray(source, Map.class);
		} catch (Exception e) {
			throw new RuntimeException("JsonToMapError " + source + " " + e.getMessage(), e);
		}
	}

	public static <T> List<T> toList(Class<T> type, String source) {
		try {
			return JSON.parseArray(source, type);
		} catch (Exception e) {
			throw new RuntimeException("JsonToMapError " + source + " " + e.getMessage(), e);
		}
	}

	public static <T> T fromJson(Class<T> type, String json) {
		try {
			return JSON.parseObject(json, type);
		} catch (Exception e) {
			throw new JSONException(type.getName() + ", " + json + ", " + e.getMessage(), e);
		}
	}

	public static String toMapJson(Object... items) {
		return toJson(Maps.toMap(items));
	}

}