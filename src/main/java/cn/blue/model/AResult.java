package cn.blue.model;

import java.util.HashMap;

/**
 * 用于layui的ajax
 * 
 * @author Blue
 *
 */
public class AResult extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AResult() {
	}

	public static AResult success() {
		return new AResult().setCode(0);
	}

	public static AResult error(int code) {
		return new AResult().setCode(code);
	}

	public AResult add(String key, Object value) {
		this.put(key, value);
		return this;
	}

	public AResult setCode(int code) {
		this.put("code", code);
		return this;
	}

	public int getCode() {
		return this.getValue("code");
	}

	public AResult setMsg(String msg) {
		this.put("msg", msg);
		return this;
	}

	public String getMsg() {
		return this.getValue("msg");
	}

	public <T> T getValue(String key) {
		return (T) this.get(key);
	}
}
