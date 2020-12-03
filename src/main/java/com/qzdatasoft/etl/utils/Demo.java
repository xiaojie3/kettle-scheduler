package com.qzdatasoft.etl.utils;

import org.quartz.SchedulerException;

public class Demo {
	public static void main(String[] args) throws SchedulerException{
		QuartzUtil quartzService = new QuartzUtil();
		quartzService.addJob("jobName", "jobGroupName", "triggerName", "triggerGroupName", DemoJob.class, "0/30 * * * * ?");
	}
}
