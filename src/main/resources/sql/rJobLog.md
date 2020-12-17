list
===
select *
from (select row_.*, rownum as rownum_
from (select a.object_id, a.name, c.executed, c.last_date
from r_job_pojo a
left join (select b.object_id,
count(1) as executed,
to_char(max(b.stop_time),'yyyy-mm-dd hh24:mi:ss') as last_date
from r_job_log b
group by b.object_id) c
on a.object_id = c.object_id
where 1 = 1
order by c.last_date desc nulls last) row_
where rownum <= #{page * limit})
where rownum_ >= #{(page - 1) * limit + 1}

count
===
select count(1) from (select a.object_id, a.name, c.executed, c.last_date
from r_job_pojo a
left join (select b.object_id,
count(1) as executed,
to_char(max(b.stop_time),'yyyy-mm-dd hh24:mi:ss') as last_date
from r_job_log b
group by b.object_id) c
on a.object_id = c.object_id
where 1 = 1
order by c.last_date desc nulls last)

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