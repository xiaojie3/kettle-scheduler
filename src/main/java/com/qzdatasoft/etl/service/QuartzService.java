package com.qzdatasoft.etl.service;

import org.quartz.SchedulerException;

import com.qzdatasoft.etl.pojo.Limit;

public interface QuartzService {
	
	/*
	 * @param page 分页页数
	 * */
	
	Limit query(Integer page,Integer limit, String cxzd, String jsfh, String cxzf);
	
	void startOnece(Long objectId, Integer repoId) throws SchedulerException;
	
	void stop(Long objectId, Integer repoId);
	
	void stertPath(String path,String cron) throws SchedulerException;
}
