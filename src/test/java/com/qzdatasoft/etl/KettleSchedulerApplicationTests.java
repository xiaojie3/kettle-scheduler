package com.qzdatasoft.etl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qzdatasoft.etl.mapper.RJobLogMapper;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KettleSchedulerApplicationTests {
	
	
	@Autowired
	private Scheduler scheduler;
	
	@Resource
	private RJobLogMapper rJobLogMapper;
	
	@Test
	public void contextLoads() throws SchedulerException {
		
		System.out.println(rJobLogMapper.list(null,null,null,null,null).size());
	}
}
