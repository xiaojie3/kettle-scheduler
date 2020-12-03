package com.qzdatasoft.etl.utils;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qzdatasoft.etl.mapper.RJobPojoMapper;
import com.qzdatasoft.etl.pojo.RJobPojo;
import com.qzdatasoft.etl.service.JobLogService;
import com.qzdatasoft.etl.serviceImpl.JobServiceImpl;
@DisallowConcurrentExecution
public class BatchJobQuartz implements Job {

	private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
	@Autowired
	private RJobPojoMapper rJobPojoMapper;
	
	@Autowired
	private JobLogService jobLogService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		List<RJobPojo> list = rJobPojoMapper.getJobByPath(jobDataMap.getString("path"));
		logger.debug(jobDataMap.getString("path"));
		for (RJobPojo rJobPojo : list) {
			logger.debug(rJobPojo.getPath() + rJobPojo.getName());
			jobLogService.insert(KettleUtil.runTranslate(rJobPojo));
		}
	}
	
	
}
