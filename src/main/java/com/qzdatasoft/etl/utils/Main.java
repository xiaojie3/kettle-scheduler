package com.qzdatasoft.etl.utils;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ProgressMonitorListener;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.logging.LoggingBuffer;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

public class Main {
	public static void main(String[] args) throws KettleException {
		String str = "jdbc:oracle:thin:@localhost:1521:orcl";
		int i = str.indexOf("@");
		int j = str.indexOf(":",i);
		String host = str.substring(i + 1,j);
		System.out.println(host);
		/*KettleEnvironment.init();
		DatabaseMeta databaseMeta = new DatabaseMeta(null, "ORACLE", "Native", "localhost", "orcl", "1521", "kettle",
				"kettle");
		KettleDatabaseRepositoryMeta repositoryInfo = new KettleDatabaseRepositoryMeta();
		repositoryInfo.setConnection(databaseMeta);
		
		KettleDatabaseRepository kettleDatabaseRepository = new KettleDatabaseRepository();
		kettleDatabaseRepository.init((RepositoryMeta) repositoryInfo);
		kettleDatabaseRepository.connect("admin", "admin");
		RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree().findDirectory("/增量工作");
	      JobMeta jobMeta = kettleDatabaseRepository.loadJob("JX02增量增加", directory, (ProgressMonitorListener)new ProgressNullMonitorListener(), null);
	      Job job = new Job((Repository)kettleDatabaseRepository, jobMeta);
	      job.setDaemon(true);
	      job.setLogLevel(LogLevel.DEBUG);
	      try {
	        job.run();
	        job.waitUntilFinished();
	      } catch (Exception e) {
	      } finally {
	    	  System.out.println("1111111111111111111111");
	      }*/
	}

	public static void runTranslate(KettleDatabaseRepository repository, RepositoryDirectoryInterface directory,
			String transName) throws KettleException {

		// TransMeta transMeta = repository.loadTransformation(transName,
		// directory, new ProgressNullMonitorListener(), true, "1.0");
		TransMeta transMeta = new TransMeta(transName);
		transMeta.setCapturingStepPerformanceSnapShots(true);
		Trans trans = new Trans(transMeta);
		trans.setLogLevel(LogLevel.BASIC);
		trans.setMonitored(true);
		trans.setInitializing(true);
		trans.setPreparing(true);
		trans.setRunning(true);
		trans.setSafeModeEnabled(true);
		trans.execute(null);
		trans.waitUntilFinished();
		if (trans.isFinished()) {
			System.out.println("执行成功");
			/*
			 * Map<String, List<StepPerformanceSnapShot>>
			 * stepPerformanceSnapShots = trans.getStepPerformanceSnapShots();
			 * stepPerformanceSnapShots.forEach((str,
			 * StepPerformanceSnapShotList) -> { for (StepPerformanceSnapShot
			 * stepPerformanceSnapShot : StepPerformanceSnapShotList){
			 * System.out.println(JSONObject.fromObject(stepPerformanceSnapShot)
			 * .toString()); } });
			 */

			String logChannelId = trans.getLogChannelId();
			LoggingBuffer appender = KettleLogStore.getAppender();
			String logText = appender.getBuffer(logChannelId, true).toString();
			System.out.println(logText);
		} else {
			System.out.println("执行失败");
		}
	}

	public static void runJob(KettleDatabaseRepository repository, RepositoryDirectoryInterface directory,
			String jobName) throws KettleException {
		JobMeta jobMeta = repository.loadJob(jobName, directory, new ProgressNullMonitorListener(), null);
		Job job = new Job(repository, jobMeta);
		job.setDaemon(true);
		job.setLogLevel(LogLevel.DEBUG);
		job.run();
		job.waitUntilFinished();
		if (job.isFinished()) {
			String logChannelId = job.getLogChannelId();
			LoggingBuffer appender = KettleLogStore.getAppender();
			String logText = appender.getBuffer(logChannelId, true).toString();
			System.out.println(logText);
		} else {
			System.out.println("执行失败");
		}
	}
}
