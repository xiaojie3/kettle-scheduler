package com.qzdatasoft.etl.serviceImpl;

import com.qzdatasoft.etl.pojo.Limit;
import com.qzdatasoft.etl.pojo.RJobPojo;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qzdatasoft.etl.mapper.RJobLogMapper;
import com.qzdatasoft.etl.pojo.RJobLog;
import com.qzdatasoft.etl.service.JobLogService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("jobLogService")
public class JobLogServiceImpl implements JobLogService {

    @Resource
    private RJobLogMapper jobLogMapper;

    @Override
    public void insert(RJobLog jobLog) {
        // TODO Auto-generated method stub
        if (null != jobLog) {
            jobLogMapper.insert(jobLog);
        }
    }

    @Override
    public Limit list(Integer page, Integer limit, String cxzd, String jsfh, String cxzf) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = jobLogMapper.list(page, limit, cxzd, jsfh, cxzf);
        Limit limit1 = new Limit();
        limit1.setRows(list);
        limit1.setCount(jobLogMapper.count(cxzd, jsfh, cxzf));
        return limit1;
    }
}
