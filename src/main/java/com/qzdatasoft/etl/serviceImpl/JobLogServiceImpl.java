package com.qzdatasoft.etl.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qzdatasoft.etl.mapper.RJobLogMapper;
import com.qzdatasoft.etl.pojo.RJobLog;
import com.qzdatasoft.etl.service.JobLogService;
@Service("jobLogService")
public class JobLogServiceImpl implements JobLogService{

	@Autowired
	private RJobLogMapper jobLogMapper;
	
	@Override
	public void insert(RJobLog jobLog) {
		// TODO Auto-generated method stub
		if(null != jobLog) {
			jobLogMapper.insert(jobLog);
		}
	}

}
