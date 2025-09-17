package com.bit2025.mysite.dto;

public class JsonResult {
	private String result;  // "success" or "fail"
	private Object data;    // if success, set
	private String message; // if fail, set

	private JsonResult() {
	}

	public static JsonResult fail(String message) {
		return new JsonResult(message);
	}

	public static JsonResult success(Object data) {
		return new JsonResult(data);
	}

	private JsonResult(String message) {
		result = "fail";
		this.message = message;
		data = null;
	}

	private JsonResult(Object data) {
		result = "success";
		this.data = data;
		message = null;
	}

	public String getResult() {
		return result;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

}
