package com.qzdatasoft.etl.mapper;

import java.util.List;
import java.util.Map;

import org.beetl.sql.mapper.BaseMapper;

import com.qzdatasoft.etl.pojo.RJobLog;

public interface RJobLogMapper extends BaseMapper<RJobLog>{
	List<Map<String, Object>> list(Integer page, Integer limit, String cxzd, String jsfh, String cxzf);
}
