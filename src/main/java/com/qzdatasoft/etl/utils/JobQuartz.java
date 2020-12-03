package com.qzdatasoft.etl.utils;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qzdatasoft.etl.serviceImpl.JobServiceImpl;

public class JobQuartz implements Job {
	private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		logger.debug(jobDataMap.getString("jobPath"));
		logger.debug(jobDataMap.getString("jobName"));
	}
}
