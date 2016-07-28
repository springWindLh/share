package com.share.support.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * BASE64可逆算法加密器
 * 
 */
public class Base64Encrypter {
	private Base64Encrypter() {
	}

	public static String encrypt(Object source) {
		byte[] data;
		try {
			if (source instanceof File) {
				data = FileUtils.readFileToByteArray((File) source);
			} else if (source instanceof InputStream) {
				data = IOUtils.toByteArray((InputStream) source);
			} else if (source instanceof Reader) {
				data = IOUtils.toByteArray((Reader) source);
			} else if (source instanceof byte[]) {
				data = (byte[]) source;
			} else {
				data = source.toString().getBytes();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		// return Base64.encode(data).replaceAll("\n", "");
		return Base64.encodeBase64String(data).replaceAll("\n", "");
	}

	public static String decrypt(String encryptedText) {
		// byte[] bytes = Base64.decode(encryptedText);
		byte[] bytes = Base64.decodeBase64(encryptedText);
		return bytes == null ? null : new String(bytes);
	}
}
