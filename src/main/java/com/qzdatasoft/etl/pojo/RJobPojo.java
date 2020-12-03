package com.qzdatasoft.etl.pojo;

import java.io.Serializable;
import java.util.Date;

import org.beetl.sql.annotation.entity.AssignID;
import org.beetl.sql.annotation.entity.Table;

import lombok.Data;

@Table(name = "R_JOB_POJO")
@Data
public class RJobPojo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RJobPojo() {}
	
	public RJobPojo(Long objectId,Integer repoId) {
		this.objectId = objectId;
		this.repoId = repoId;
	}
	
	@AssignID
	private Long objectId;
	private String name;
	private String path;
	private String description;
	private String logLevel;
	private String status;
	private Date modifiedDate;
	private Integer repoId;
	private String cron;
	private String quarStatus;
}