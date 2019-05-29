package com.tensquare.article.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_channel")
@Data
public class Channel implements Serializable {
	@Id
	private String id;//ID

	private String name;//频道名称

	private String state;//状态

}
