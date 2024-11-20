package com.poc.service;

import com.poc.request.JobParametersRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("firstJob")
    @Autowired
    Job fisrtJob;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;

    @Async
    public void startJob(String jobName, List<JobParametersRequest> jobParametersRequest) {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));

        jobParametersRequest.stream().forEach(jobParamRequest -> {
            params.put(jobParamRequest.getParamKey(),
                    new JobParameter(jobParamRequest.getParamValue()));
        });

        JobParameters jobParameters = new JobParameters(params);

        try {
            JobExecution jobExecution = null;
            if (jobName.equals("First Job")) {
                jobExecution =jobLauncher.run(fisrtJob, jobParameters);
            } else if (jobName.equals("Second Job")) {
                jobExecution =jobLauncher.run(secondJob, jobParameters);
            }
            System.out.println("JobExecution ID: " + jobExecution.getId());
        }catch (Exception e) {
            System.out.println("Error in starting job");
        }
    }
}
