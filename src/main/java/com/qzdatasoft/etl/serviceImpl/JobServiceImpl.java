package com.qzdatasoft.etl.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qzdatasoft.etl.mapper.RJobPojoMapper;
import com.qzdatasoft.etl.pojo.RJobPojo;
import com.qzdatasoft.etl.service.JobLogService;
import com.qzdatasoft.etl.service.JobService;
import com.qzdatasoft.etl.utils.KettleUtil;
@Service
public class JobServiceImpl implements JobService{
	
	private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

	@Autowired
	private RJobPojoMapper rJobPojoMapper;
	
	@Autowired
	private JobLogService jobLogService;
	
	@Override
	public void doStartById(Long objectId, Integer repoId) {
		// TODO Auto-generated method stub
		
		RJobPojo jobPojo = rJobPojoMapper.single(new RJobPojo(objectId,repoId));
		if(null == jobPojo) {
			logger.debug("未找到作业！");
		} else {
			logger.debug(String.format("执行作业->ID:%s, 名称：%s", objectId,jobPojo.getName()));
			jobLogService.insert(KettleUtil.runTranslate(jobPojo));
		}
	}

}
