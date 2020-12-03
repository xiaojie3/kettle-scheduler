package com.qzdatasoft.etl.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Limit {
	private Integer page;
	private Integer limit;
	private Long count;
	private List<?> rows;
}
