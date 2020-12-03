package com.qzdatasoft.etl.controller;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qzdatasoft.etl.pojo.Limit;
import com.qzdatasoft.etl.service.QuartzService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/quartzJob")
public class QuartzController {

	@Autowired
	private QuartzService quartzService;

	@Autowired
	private Scheduler scheduler;

	@GetMapping("/doStart/{repoId}/{objectId}")
	public ResponseEntity<String> doStartById(@PathVariable Long objectId,@PathVariable Integer repoId) {
		try {
			quartzService.startOnece(objectId, repoId);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body("定时运行中");
	}

	@GetMapping("/doStartByPath")
	public ResponseEntity<String> doStartByPath(String path, String cron) {
		try {
			quartzService.stertPath(path, cron);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body("启动成功");
	}

	@GetMapping("/doStop/{repoId}/{objectId}")
	public ResponseEntity<String> doStop(@PathVariable Long objectId,@PathVariable Integer repoId) {
		quartzService.stop(objectId, repoId);
		return ResponseEntity.status(HttpStatus.OK).body("定时已停止");
	}

	@GetMapping("/startup")
	public ResponseEntity<String> startup() throws SchedulerException {
		if (scheduler.isShutdown()) {
			scheduler.start();
		}
		return ResponseEntity.status(HttpStatus.OK).body("定时作业已启动");
	}

	@PostMapping("/shutdown")
	public ResponseEntity<String> shutdown() throws SchedulerException {
		if (scheduler.isStarted()) {
			scheduler.shutdown();
			return ResponseEntity.status(HttpStatus.OK).body("定时作业已停止");
		}
		return ResponseEntity.status(HttpStatus.OK).body("定时作业未启动");
	}

	/*
	 * @param page 分页起始数
	 * @param limit 分页每页数
	 * @param cxzd 查询字段
	 * @param jsfh 计算符号
	 * @param cxzf 查询字符
	 * */
	
	@GetMapping("/list")
	public ResponseEntity<Limit> list(Integer page, Integer limit, String cxzd, String jsfh, String cxzf) {
		log.info("");
		return ResponseEntity.ok(quartzService.query(page, limit,cxzd,jsfh,cxzf));
	}
}
