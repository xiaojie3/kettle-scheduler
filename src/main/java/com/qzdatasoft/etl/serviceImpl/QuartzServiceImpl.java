package com.qzdatasoft.etl.serviceImpl;

import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qzdatasoft.etl.mapper.RJobPojoMapper;
import com.qzdatasoft.etl.pojo.Limit;
import com.qzdatasoft.etl.pojo.RJobPojo;
import com.qzdatasoft.etl.service.QuartzService;
import com.qzdatasoft.etl.utils.BatchJobQuartz;
import com.qzdatasoft.etl.utils.JobQuartz;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuartzServiceImpl implements QuartzService {
	@Autowired
	private RJobPojoMapper rJobPojoMapper;

	@Autowired
	private Scheduler scheduler;

	@Override
	public void startOnece(Long objectId, Integer repoId) throws SchedulerException {
		// TODO Auto-generated method stub
		RJobPojo job = new RJobPojo();
		job.setObjectId(objectId);
		job.setRepoId(repoId);
		job = this.rJobPojoMapper.unique(job);

		JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(job.getName(), job.getPath()));
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
				.build();
		if (null == jobDetail) {
			log.info("创建任务");
			JobDataMap newJobDataMap = new JobDataMap();
			newJobDataMap.put("jobPath", job.getPath());
			newJobDataMap.put("jobName", job.getName());
			jobDetail = JobBuilder.newJob(JobQuartz.class).withIdentity(job.getName(), job.getPath())
					// JobDataMap可以给任务传递参数
					.usingJobData(newJobDataMap).build();
		} else {
			log.info("修改任务");
			scheduler.deleteJob(JobKey.jobKey(job.getName(), job.getPath()));
		}
		scheduler.scheduleJob(jobDetail, trigger);
		job.setQuarStatus("定时运行中");
		rJobPojoMapper.updateById(job);
	}

	@Override
	public void stertPath(String path, String cron) throws SchedulerException {
		// TODO Auto-generated method stub
		log.debug("定时作业：" + path + "[" + cron + "]");
		// 新建触发器
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
		JobKey jobKey = JobKey.jobKey("每晚23点", path);
		// 获取作业运行状态
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		JobDataMap newJobDataMap = new JobDataMap();
		newJobDataMap.put("path", path);
		jobDetail = JobBuilder.newJob(BatchJobQuartz.class).withIdentity("每晚23点", path)
				// JobDataMap可以给任务传递参数
				.usingJobData(newJobDataMap).build();
		if (scheduler.checkExists(jobKey)) {
			scheduler.deleteJob(jobKey);
			log.info("定时作业已启动，进行关闭。");
		}
		scheduler.scheduleJob(jobDetail, trigger);
	}

	@Override
	public void stop(Long objectId, Integer repoId) {
		// TODO Auto-generated method stub
		RJobPojo job = new RJobPojo();
		job.setObjectId(objectId);
		job.setRepoId(repoId);
		job = rJobPojoMapper.unique(job);
		JobKey jobKey = JobKey.jobKey(job.getName(), job.getPath());
		try {
			if (scheduler.checkExists(jobKey)) {
				scheduler.deleteJob(jobKey);
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		job.setQuarStatus("定时已停止");
		rJobPojoMapper.updateById(job);
	}

	/*
	 * @param page 分页页数
	 */
	@Override
	public Limit query(Integer page, Integer limit, String cxzd, String jsfh, String cxzf) {
		// TODO Auto-generated method stub
		LambdaQuery<RJobPojo> lambdaQuery = rJobPojoMapper.createLambdaQuery().asc("path").asc("name");
		log.info(String.valueOf(null != cxzf));
		if (null != cxzf) {
			switch (jsfh) {
			case "like":
				lambdaQuery = lambdaQuery.andLike(cxzd, "%" + cxzf + "%");
				break;
			case "not like":
				lambdaQuery = lambdaQuery.andNotLike(cxzd, "%" + cxzf + "%");
				break;
			case "=":
				lambdaQuery = lambdaQuery.andEq(cxzd, cxzf);
				break;
			case "<>":
				lambdaQuery = lambdaQuery.andNotEq(cxzd, cxzf);
				break;
			case ">":
				lambdaQuery = lambdaQuery.andGreat(cxzd, cxzf);
				break;
			case ">=":
				lambdaQuery = lambdaQuery.andGreatEq(cxzd, cxzf);
				break;
			case "<":
				lambdaQuery = lambdaQuery.andLess(cxzd, cxzf);
				break;
			case "<=":
				lambdaQuery = lambdaQuery.andLessEq(cxzd, cxzf);
				break;
			}
		}
		PageResult<RJobPojo> query = lambdaQuery.page(page, limit);
		Limit l = new Limit();
		l.setRows(query.getList());
		l.setCount(query.getTotalRow());
		return l;
	}
}
