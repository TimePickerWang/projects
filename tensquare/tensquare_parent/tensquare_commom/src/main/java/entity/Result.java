package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by wangjf
 * Date 2019/4/16 16:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
	private boolean flag;
	private Integer code;
	private String message;
	private Object data;


	public Result(boolean flag, Integer code, String message) {
		this.flag = flag;
		this.code = code;
		this.message = message;
	}
}
