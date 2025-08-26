package com.bit2025.mysite.vo;

public class GuestbookVo {
	private Long id;
	private String Name;
	private String password;
	private String message;
	private String regDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "GuestbookVo [id=" + id + ", Name=" + Name + ", password=" + password + ", message=" + message
				+ ", regDate=" + regDate + "]";
	}
	
	
}