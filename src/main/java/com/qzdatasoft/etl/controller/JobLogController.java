package com.qzdatasoft.etl.controller;

import com.qzdatasoft.etl.pojo.Limit;
import com.qzdatasoft.etl.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/jobLog")
public class JobLogController {

    @Autowired
    private JobLogService jobLogService;

    @GetMapping()
    public ResponseEntity<Limit> list(Integer page, Integer limit, String cxzd, String jsfh, String cxzf) {
        return ResponseEntity.ok(jobLogService.list(page,limit,cxzd,jsfh,cxzf));
    }
}
