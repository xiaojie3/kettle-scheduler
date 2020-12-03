package com.qzdatasoft.etl.service;

import com.qzdatasoft.etl.pojo.RJobPojo;

public interface KettleService {
	public void runTranslate(RJobPojo jobPojo);
}
