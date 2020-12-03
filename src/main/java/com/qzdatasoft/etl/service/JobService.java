package com.qzdatasoft.etl.service;

import org.pentaho.di.core.exception.KettleException;

public interface JobService {
	void doStartById(Long objectId, Integer repoId) throws KettleException;
	
}
