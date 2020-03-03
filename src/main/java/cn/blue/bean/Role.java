package cn.blue.bean;

import java.util.List;

import lombok.Data;

@Data
public class Role {
	private Integer id;
	private String name;
	private Integer status;
	private boolean checked;

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	private List<UserInfo> users;

	private List<Power> powers;
}
