package com.tensquare.spit.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Create by wangjf
 * Date 2019/5/28 18:20
 */
@Data
public class Spit implements Serializable {
	@Id
	private String _id;

	private String content;

	private Date publishtime;

	private String userid;

	private String nickname;

	private Integer visits;

	private Integer thumbup;

	private Integer share;

	private Integer comment;

	private String state;

	private String parentid;

}
