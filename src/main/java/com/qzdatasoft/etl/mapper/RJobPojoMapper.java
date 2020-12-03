package com.qzdatasoft.etl.mapper;

import java.util.List;

import org.beetl.sql.mapper.BaseMapper;
import org.beetl.sql.mapper.annotation.Param;

import com.qzdatasoft.etl.pojo.RJobPojo;


public interface RJobPojoMapper extends BaseMapper<RJobPojo> {
	List<RJobPojo> getJobByPath(@Param("path") String path);
}