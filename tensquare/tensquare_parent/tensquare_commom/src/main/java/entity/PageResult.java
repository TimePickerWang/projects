package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Create by wangjf
 * Date 2019/4/16 16:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
	private long total;
	private List<T> rows;

}
