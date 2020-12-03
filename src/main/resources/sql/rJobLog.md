list
===
	select a.object_id, a.name, c.executed, c.lastDate
	  from r_job_pojo a
	  left join (select b.object_id,
	                    count(1) as executed,
	                    max(b.stop_time) as lastDate
	               from r_job_log b
	              group by b.object_id) c
	    on a.object_id = c.object_id
	 order by c.lastDate desc nulls last