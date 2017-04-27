package com.youmu.maven;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

public class JsonView extends AbstractView {

	public String content;

	public JsonView(String json) {
		// TODO Auto-generated constructor stub
		setContentType("application/json; charset=utf-8");
		this.content = json;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		ByteArrayOutputStream stream = createTemporaryOutputStream();
		stream.write(content.getBytes());

		writeToResponse(response, stream);
	}

	class C implements Cloneable{
		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static void main(String[] args) {

	}
}