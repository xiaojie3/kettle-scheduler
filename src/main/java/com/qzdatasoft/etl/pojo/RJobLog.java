package com.qzdatasoft.etl.pojo;

import java.util.Date;

import org.beetl.sql.annotation.entity.SeqID;

import lombok.Data;
@Data
public class RJobLog {
	@SeqID(name = "LOG_AUTO_ID")
	private Long id;

	private Long objectId;

	private Integer repoId;

	private Date startTime;

	private Date stopTime;

	private Integer status;

	private String logPath;
}
