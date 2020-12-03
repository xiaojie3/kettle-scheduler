getJobByPath
===
	select t.*,t.rowid from R_JOB_POJO t where t.path = #{path} order by t.path,t.name