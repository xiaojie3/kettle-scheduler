package com.qzdatasoft.etl.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ProgressMonitorListener;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.springframework.util.ClassUtils;

import com.qzdatasoft.etl.pojo.RJobLog;
import com.qzdatasoft.etl.pojo.RJobPojo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KettleUtil {
	public static KettleDatabaseRepository kettleDatabaseRepository;

	public static void init() throws KettleException {
		KettleEnvironment.init();
		DatabaseMeta databaseMeta = new DatabaseMeta(null, "ORACLE", "Native", "192.168.164.200", "orcl", "1521", "kettle",
				"kettle");
		KettleDatabaseRepositoryMeta repositoryInfo = new KettleDatabaseRepositoryMeta();
		repositoryInfo.setConnection(databaseMeta);

		kettleDatabaseRepository = new KettleDatabaseRepository();
		kettleDatabaseRepository.init((RepositoryMeta) repositoryInfo);
		kettleDatabaseRepository.connect("admin", "admin");
	}

	public static synchronized RJobLog runTranslate(RJobPojo jobPojo) {
		log.info("运行了：" + jobPojo.toString());
		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;*/
		Date jobStartDate = null;
		Integer recordStatus = Integer.valueOf(1);
		Date jobStopDate = null;
		String logText = null;
		RJobLog jobLog = new RJobLog();
		StringBuilder allLogFilePath = new StringBuilder();
		try {
			if (null == kettleDatabaseRepository) {
				init();
			}
			RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree()
					.findDirectory(jobPojo.getPath());
			JobMeta jobMeta = kettleDatabaseRepository.loadJob(jobPojo.getName(), directory,
					(ProgressMonitorListener) new ProgressNullMonitorListener(), null);
			Job job = new Job((Repository) kettleDatabaseRepository, jobMeta);
			job.setDaemon(true);
			job.setLogLevel(LogLevel.BASIC);
			jobStartDate = new Date();
			job.run();
			job.waitUntilFinished();
			jobStopDate = new Date();
			if (job.getErrors() > 0) {
				recordStatus = Integer.valueOf(0);
				logText = job.getResult().getLogText();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
			allLogFilePath.append(ClassUtils.getDefaultClassLoader().getResource("").getPath()).append("log/")
					.append("@").append(jobPojo.getObjectId()).append(jobPojo.getName()).append("-log").append("/")
					.append(sdf.format(new Date())).append(".").append("txt");
			log.info(allLogFilePath.toString());
			FileUtils.writeStringToFile(new File(allLogFilePath.toString()), logText);
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			jobLog.setObjectId(jobPojo.getObjectId());
			jobLog.setRepoId(jobPojo.getRepoId());
			jobLog.setStartTime(jobStartDate);
			jobLog.setStopTime(jobStopDate);
			jobLog.setStatus(recordStatus);
			jobLog.setLogPath(allLogFilePath.toString());
		}
		return jobLog;
	}
}
