package com.poc.controller;


import com.poc.request.JobParametersRequest;
import com.poc.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.batch.operations.JobOperator;
import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    JobService jobService;

//    @Autowired
//    JobOperator jobOperator;

    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName,
                           @RequestBody List<JobParametersRequest> jobParametersRequest
                           ) throws Exception {
        jobService.startJob(jobName, jobParametersRequest);
        return "Job Started ........";
    }

//    @GetMapping("/stop/{jobExecutionId}")
//    public String stopJob(@PathVariable long jobExecutionId) {
//
//        try {
//            jobOperator.stop(jobExecutionId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "Job Stopped ........";
//    }
}
