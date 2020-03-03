package cn.blue.bean.vo;

import java.util.HashMap;
import java.util.Map;

public class Result extends HashMap<String, Object> {

	public static Result success() {
		return new Result().setCode(0);
	}

	public static Result error(int code, String msg) {
		return new Result().setCode(code).setMsg(msg);
	}
	
	public Result addItem(String key,Object value) {
		this.put(key, value);
		return this;
	}

	public Result setCode(int code) {
		this.put("code", code);
		return this;
	}

	public Result setMsg(String msg) {
		this.put("msg", msg);
		return this;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getData() {
		return (Map<String, Object>) this.get("data");
	}

	public Result addData(String key, Object value) {
		if (!this.containsKey("data")) {
			this.put("data", new HashMap<>());
		}
		this.getData().put(key, value);
		return this;
	}
}
