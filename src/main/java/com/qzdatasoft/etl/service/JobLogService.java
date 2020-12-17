package com.qzdatasoft.etl.service;

import com.qzdatasoft.etl.pojo.Limit;
import com.qzdatasoft.etl.pojo.RJobLog;

import java.util.List;
import java.util.Map;

public interface JobLogService {
	void insert(RJobLog jobLog);
	Limit list(Integer page, Integer limit, String cxzd, String jsfh, String cxzf);
}
