package com.qzdatasoft.etl.utils;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzUtil {
	private Scheduler scheduler;

	@PostConstruct
	public void startScheduler() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除job
	 *
	 * @param triggerName
	 *            触发器名称
	 * @param triggerGroup
	 *            触发器分组
	 * @param jobName
	 *            任务名称
	 * @param jobGroup
	 *            任务分组
	 * @throws SchedulerException
	 */
	public void deleteJob(String triggerName, String triggerGroup, String jobName, String jobGroup)
			throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
		scheduler.pauseTrigger(triggerKey);
		scheduler.unscheduleJob(triggerKey);
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 修改定时任务
	 *
	 * @param oldTriggerKey
	 *            需要修改的TriggerKey 也就是唯一标识
	 * @param cron
	 *            新的cron表达式
	 */
	public void updateJob(TriggerKey oldTriggerKey, String cron) {
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
		CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(oldTriggerKey).withSchedule(scheduleBuilder)
				.build();
		try {
			scheduler.rescheduleJob(oldTriggerKey, cronTrigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新增job任务
	 *
	 * @param jobName
	 *            job名称
	 * @param jobGroupName
	 *            job分组名称
	 * @param triggerName
	 *            触发器名称
	 * @param triggerGroupName
	 *            触发器分组名称
	 * @param jobClass
	 *            需要执行的job.class
	 * @param cron
	 *            cron 表达式
	 * @throws SchedulerException
	 */
	public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
			Class<? extends QuartzJobBean> jobClass, String cron) throws SchedulerException {
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
				.withSchedule(cronScheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}
}
