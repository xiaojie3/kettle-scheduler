list
===
select *
from (select row_.*, rownum as rownum_
from (select a.object_id, a.name, c.executed, c.last_date
from r_job_pojo a
left join (select b.object_id,
count(1) as executed,
max(b.stop_time) as last_date
from r_job_log b
group by b.object_id) c
on a.object_id = c.object_id
where 1 = 1
order by c.last_date desc nulls last) row_
where rownum <= 10)
where rownum_ >= 0

select 
===
select a.object_id, a.name, c.executed, c.last_date
from r_job_pojo a
left join (select b.object_id,
count(1) as executed,
max(b.stop_time) as last_date
from r_job_log b
group by b.object_id) c
on a.object_id = c.object_id