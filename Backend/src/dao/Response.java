package dao;

import java.util.List;

public class Response {
	private String message;
	private int status;
	private List<?> data;
	public Response() {}
	public Response(String message, int status, List<?> data) {
		super();
		this.message = message;
		this.status = status;
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
}
