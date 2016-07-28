package com.share.support.util;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

public class TextView implements View {
	private String text;

	private String contentType = "text/html;charset=UTF-8";

	private String characterEncoding = "UTF-8";

	public TextView() {
	}

	public TextView(String text) {
		this.text = text;
	}

	public TextView(String text, String contentType) {
		setText(text);
		setContentType(contentType);
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding(getCharacterEncoding());
		response.setContentType(getContentType());
		PrintWriter out = response.getWriter();
		out.print(text);
		out.flush();
		out.close();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return super.toString() + " text=" + getText() + ", contentType=" + getContentType() + ", characterEncoding="
				+ getCharacterEncoding();
	}
}