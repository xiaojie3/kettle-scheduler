package com.qzdatasoft.etl.controller;

import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qzdatasoft.etl.service.JobService;

import lombok.extern.slf4j.Slf4j;
/**
* @Description: 手动操作作业
* @date 2020年11月23日
*/
@Slf4j
@RestController
@RequestMapping("/job")
public class JobController {
	
	@Autowired
	private JobService jobservice;
	/*
	 * @method 启动单个作业
	 * @param objectId 作业ID
	 * @param repoId 资源库ID
	 * 
	 * */
	@GetMapping("/start/{repoId}/{objectId}")
	public ResponseEntity<String> start(@PathVariable Long objectId,@PathVariable Integer repoId) {
		log.debug(objectId + "" + repoId);
		try {
			jobservice.doStartById(objectId, repoId);
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("启动成功");
	}
}
